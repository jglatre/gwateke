package javax.swing;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import junit.framework.TestCase;


public class DefaultListSelectionModelTest extends TestCase {
	
	private DefaultListSelectionModel model;
	private EventCatcher listener;

	
	public void setUp() throws Exception {
		model = new DefaultListSelectionModel();
		listener = new EventCatcher();
		model.addListSelectionListener(listener);
	}
	
	
	public void testSingleSelection() {		
		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		assertEquals(ListSelectionModel.SINGLE_SELECTION, model.getSelectionMode());
		assertTrue(model.isSelectionEmpty());
		
		model.setSelectionInterval(1, 5);
		
		assertFalse(model.isSelectionEmpty());
		assertTrue(model.isSelectedIndex(5));
		assertEquals(5, model.getMinSelectionIndex());
		assertEquals(5, model.getMaxSelectionIndex());
		
		assertNotNull(listener.event);
		assertEquals(5, listener.event.getFirstIndex());
		assertEquals(5, listener.event.getLastIndex());
		
		model.clearSelection();
		
		assertTrue(model.isSelectionEmpty());
	}
	
	
	public void testMultiInterval() {
		model.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		assertEquals(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, model.getSelectionMode());
		assertTrue(model.isSelectionEmpty());
		
		model.setSelectionInterval(1, 5);
		assertNotNull(listener.event);
		assertEquals(1, listener.event.getFirstIndex());
		assertEquals(5, listener.event.getLastIndex());
		
		model.addSelectionInterval(100, 100);
		assertNotNull(listener.event);
		assertEquals(100, listener.event.getFirstIndex());
		assertEquals(100, listener.event.getLastIndex());
		
		assertFalse(model.isSelectionEmpty());
		assertFalse(model.isSelectedIndex(0));
		for (int i = 1; i <= 5; i++) {
			assertTrue(model.isSelectedIndex(i));
		}
		for (int i = 6; i <= 99; i++) {
			assertFalse(model.isSelectedIndex(i));
		}
		assertTrue(model.isSelectedIndex(100));
				
		model.removeSelectionInterval(100, 100);

		assertEquals(1, model.getMinSelectionIndex());
		assertEquals(5, model.getMaxSelectionIndex());
		
		model.clearSelection();
		
		assertTrue(model.isSelectionEmpty());
	}
	
	
	private static class EventCatcher implements ListSelectionListener {
		ListSelectionEvent event;		
		public void valueChanged(ListSelectionEvent event) {
			this.event = event;			
		}	
	}
}
