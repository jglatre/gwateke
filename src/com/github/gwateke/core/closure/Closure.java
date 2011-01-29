package com.github.gwateke.core.closure;

public interface Closure<R, P> {

	R call(P arguments);
}
