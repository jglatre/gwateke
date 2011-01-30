package com.github.gwateke.binding.form.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.gwateke.binding.PropertyMetadataAccessStrategy;
import com.github.gwateke.binding.convert.ConversionService;
import com.github.gwateke.binding.convert.support.DefaultConversionService;
import com.github.gwateke.binding.form.CommitListener;
import com.github.gwateke.binding.form.ConfigurableFormModel;
import com.github.gwateke.binding.form.FieldMetadata;
import com.github.gwateke.binding.value.CommitTrigger;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractPropertyChangePublisher;
import com.github.gwateke.binding.value.support.BufferedValueModel;
import com.github.gwateke.binding.value.support.MapKeyAdapter;
import com.github.gwateke.binding.value.support.TypeConverter;
import com.github.gwateke.binding.value.support.ValueHolder;
import com.github.gwateke.core.closure.Closure;


public class DefaultFormModel<B> extends AbstractPropertyChangePublisher implements ConfigurableFormModel<B> {
    
	private final Log log = LogFactory.getLog(getClass());
	
	private String id;
	private ValueModel<B> formObjectHolder;
	private final PropertyMetadataAccessStrategy metadataAccessStrategy;
	private boolean buffered = false;
	private boolean enabled = true;
	private ConversionService conversionService;
	private CommitTrigger commitTrigger = new CommitTrigger();
	private final Map<String,ValueModel<?>> propertyValueModels = new HashMap<String,ValueModel<?>>();
	private final Map<String,ValueModel<?>> convertingValueModels = new HashMap<String, ValueModel<?>>();
	private final Map<String, FieldMetadata> fieldMetadata = new HashMap<String, FieldMetadata>();
	private final Set<ValueModel<?>> dirtyValueModels = new HashSet<ValueModel<?>>();
	private final PropertyChangeListener dirtyChangeHandler = new DirtyChangeHandler();
	private final List<CommitListener<B>> commitListeners = new ArrayList<CommitListener<B>>();


	public DefaultFormModel(B domainObject) {
		this(domainObject, true);
//		this(new ValueHolder<Object>(domainObject), true);
	}

	
	public DefaultFormModel(B domainObject, PropertyMetadataAccessStrategy metadataAccessStrategy) {
		this(new ValueHolder<B>(domainObject), true, metadataAccessStrategy);
	}
	
    
    public DefaultFormModel(B domainObject, boolean buffered) {
        this(new ValueHolder<B>(domainObject), buffered, new DummyPropertyMetadata());
    }

    
    protected DefaultFormModel(ValueModel<B> formObjectHolder, boolean buffered, PropertyMetadataAccessStrategy metadataAccessStrategy) {
//        prepareValueModel(formObjectHolder);
        this.formObjectHolder = formObjectHolder;
        this.buffered = buffered;  
        this.metadataAccessStrategy = metadataAccessStrategy;
    }



    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public B getFormObject() {
        return getFormObjectHolder().getValue();
    }

    
    public void setFormObject(B formObject) {
//  	setDeliverValueChangeEvents(false);
    	if (formObject == null) {
    		throw new IllegalArgumentException("FormObject cannot be null");
    	}
    	
    	getFormObjectHolder().setValue(formObject);
    	setEnabled(true);

    	// this will cause all buffered value models to revert 
    	// to the new form objects property values 
    	commitTrigger.revert();
//  	setDeliverValueChangeEvents(true);
    }


    public ValueModel<B> getFormObjectHolder() {
        return formObjectHolder;
    }


    public boolean hasValueModel(String formProperty) {
        return propertyValueModels.containsKey(formProperty);
    }

    
    public ValueModel<?> getValueModel(String formProperty) {
        ValueModel<?> propertyValueModel = propertyValueModels.get(formProperty);
        if (propertyValueModel == null) {
        	propertyValueModel = add(formProperty);
        }
        return propertyValueModel;
    }
    
    
    @SuppressWarnings("unchecked")
	public <T> ValueModel<T> getValueModel(String formProperty, Class<T> targetClass) {
    	final String key = formProperty + "-" + targetClass;
		ValueModel<T> convertingValueModel = (ValueModel<T>) convertingValueModels.get(key);
		if (convertingValueModel == null) {
			convertingValueModel = createConvertingValueModel(formProperty, targetClass);
			convertingValueModels.put(key, convertingValueModel);
		}
		return convertingValueModel;
    }
    
    
    public ValueModel<?> add(String propertyName) {
    	if (metadataAccessStrategy.isReadable(propertyName)) {
        	return add(propertyName, createValueModel(propertyName));
    	}
    	else {
    		log.debug("rejected property " + propertyName);
    		return null;
    	}
    }
    
    
    public ValueModel<?> add(String formProperty, ValueModel<?> valueModel) {
        if (buffered) {
        	BufferedValueModel<?> bufferedValueModel = new BufferedValueModel(valueModel, commitTrigger);
        	bufferedValueModel.addPropertyChangeListener(BufferedValueModel.BUFFERING_PROPERTY, dirtyChangeHandler);
        	valueModel = bufferedValueModel;
        }
        
       	FieldMetadata metadata = new DefaultFieldMetadata(this, /*valueModel, */
        			metadataAccessStrategy.getPropertyType(formProperty), 
        			!metadataAccessStrategy.isWriteable(formProperty), 
        			metadataAccessStrategy.getAllUserMetadata(formProperty));

       	fieldMetadata.put(formProperty, metadata);
        propertyValueModels.put(formProperty, valueModel);
        
        return valueModel;
    }
    
    
    public FieldMetadata getFieldMetadata(String propertyName) {
        FieldMetadata metadata = fieldMetadata.get(propertyName);
        if (metadata == null) {
            add(propertyName);
            metadata = fieldMetadata.get(propertyName);
        }
        return metadata;
    }
    
    
    public String[] getFieldNames() {
    	return fieldMetadata.keySet().toArray(new String[0]);
    }
    
    
    public void commit() {
		if (log.isDebugEnabled()) {
			log.debug("[DefaultFormModel] Commit requested for this form model " + this);
		}
        
        if (getFormObject() == null) {
        	log.debug("[DefaultFormModel] Form object is null; nothing to commit.");
            return;
        }
        
        if (isCommittable()) {
            for (CommitListener<B> listener : commitListeners) {
                listener.preCommit(this);
            }
            preCommit();
            if (isCommittable()) {
            	commitTrigger.commit();   //doCommit();
                postCommit();
                for (CommitListener<B> listener : commitListeners) {
                    listener.postCommit(this);
                }
            }
            else {
                throw new IllegalStateException("Form model '" + this
                        + "' became non-committable after preCommit phase");
            }
        }
        else {
            throw new IllegalStateException("Form model '" + this + "' is not committable");
        }
    }
    
    
    public void revert() {
        commitTrigger.revert();
    }

    
    public void reset() {
    	// TODO
        //setFormObject(null);
    }


    public boolean isBuffered() {
        return buffered;
    }

    
    public boolean isDirty() {
    	return dirtyValueModels.size() > 0;
    }
    

    public void setEnabled(boolean enabled) {
    	if (!hasChanged(this.enabled, enabled)) {
    		return;
    	}
        this.enabled = enabled;
        firePropertyChange(ENABLED_PROPERTY, !enabled, enabled);
//        enabledUpdated();
    }
    

    public boolean isEnabled() {
        return enabled;
    }

    
    public boolean isCommittable() {
    	return true;
    }    
    

    public void addCommitListener(CommitListener<B> listener) {
        commitListeners.add(listener);
    }

    
    public void removeCommitListener(CommitListener<B> listener) {
        commitListeners.remove(listener);
    }

    
    public String toString() {
    	return getClass().toString() + ':' + id;
    }
    
    
    public ConversionService getConversionService() {
    	if (conversionService == null) {
    		conversionService = new DefaultConversionService();
    	}
    	return conversionService;
    }
    

    public void setConversionService(ConversionService conversionService) {
    	this.conversionService = conversionService;
    }

    //----------------------------------------------------------
    
    protected ValueModel<?> createValueModel(String formProperty) {
    	return new MapKeyAdapter<String, Object>((ValueModel<Map<String, Object>>) getFormObjectHolder(), formProperty);
    	
    	// TODO delegar usando PopertyAccessStrategy
//        return buffered ? new BufferedValueModel(propertyAccessStrategy.getPropertyValueModel(formProperty))
//        : propertyAccessStrategy.getPropertyValueModel(formProperty);    	
    }

    
    protected <T> ValueModel<T> createConvertingValueModel(String formProperty, Class<T> targetClass) {
    	final ValueModel sourceValueModel = getValueModel(formProperty);
    	String sourceClass = getFieldMetadata(formProperty).getPropertyType();
        if (sourceClass.equals(targetClass.getName())) {
            return sourceValueModel;
        }
        
        //TODO FIXME hack guarro para forzar conversión a String de los ids de campos tipo manyToOne
        Boolean association = getFieldMetadata(formProperty).getUserMetadata("association");
		if (association != null && association) {
        	sourceClass = Object.class.getName();
        }
        
		if (log.isDebugEnabled()) {
			log.debug("Creating converting value model for form property '" + formProperty
                + "' converting from type '" + sourceClass + "' to type '" + targetClass + "'.");
		}
    	
        Closure<?,?> convertTo = getConversionService().getConversionClosure(sourceClass, targetClass.getName());
        Closure<?,?> convertFrom = getConversionService().getConversionClosure(targetClass.getName(), sourceClass);
        
        ValueModel<T> convertingValueModel = (ValueModel<T>) new TypeConverter(sourceValueModel, convertTo, convertFrom);
        return convertingValueModel;
    }
    
    
    protected void preCommit() {
    }


    protected void postCommit() {
    }

    
	protected class DirtyChangeHandler implements PropertyChangeListener {
		
		public void propertyChange(PropertyChangeEvent evt) {
			Object source = evt.getSource();
			
			/* la versión original de richclient se basa en DirtyTrackingValueModel
			 * pero a efectos prácticos nos sirve la propiedad "buffering" de BufferedValueModel
			 * para saber si un ValueModel está sucio.
			 */
			if (source instanceof BufferedValueModel) {				
				boolean dirty = isDirty();
				
				BufferedValueModel<?> valueModel = (BufferedValueModel<?>) source;
				if (valueModel.isBuffering()) {
					dirtyValueModels.add(valueModel);
				}
				else {
					dirtyValueModels.remove(valueModel);
				}
				
				if (hasChanged(dirty, isDirty())) {
					firePropertyChange(DIRTY_PROPERTY, dirty, isDirty());
				}
			}
		}
	}
	
	
	private static class DummyPropertyMetadata implements PropertyMetadataAccessStrategy {

		public Map<String, ?> getAllUserMetadata(String propertyName) {
			return new HashMap<String, Object>();
		}

		public String getPropertyType(String propertyName) {
			return Object.class.getName();
		}

		public Object getUserMetadata(String propertyName, String key) {
			return null;
		}

		public boolean isReadable(String propertyName) {
			return true;
		}

		public boolean isWriteable(String propertyName) {
			return true;
		}		
	}
	
}
