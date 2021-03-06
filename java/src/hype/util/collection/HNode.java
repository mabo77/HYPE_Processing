package hype.util.collection;

public abstract class HNode<T extends HNode<T>> {
	protected T _prev, _next;
	
	public T prev() {
		return _prev;
	}
	
	public T next() {
		return _next;
	}
	
	public boolean poppedOut() {
		return (_prev==null) && (_next==null);
	}
	
	public void popOut() {
		// Stitch `_prev` and `_next` together
		if(_prev!=null) _prev._next = _next;
		if(_next!=null) _next._prev = _prev;
		
		// Remove this node from the node chain
		_prev = _next = null;
	}
	
	@SuppressWarnings("unchecked")
	public void putBefore(T dest) {
		if(dest == this) return;
		if(!poppedOut()) popOut();
		
		T p = dest._prev;
		
		// Stitch together `p` and this node
		if(p!=null) p._next = (T) this;
		_prev = p;
		
		// Stitch together this node and `dest`
		_next = dest;
		dest._prev = (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public void putAfter(T dest) {
		if(dest == this) return;
		if(!poppedOut()) popOut();
		
		T n = dest.next();
		
		// Stitch together `dest` and `target`
		dest._next = (T) this;
		_prev = dest;
		
		// Stitch together `insert` and `n`
		_next = n;
		if(n!=null) n._prev = (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public void swapLeft() {
		if(_prev==null) return;
		
		T pairPrev = _prev._prev;
		T pairNext = _next;
		
		// Swap `target` and `left`
		_next = _prev;
		_prev._prev = (T) this;
		
		// Put this node and `_prev` back into the chain
		_prev._next = pairNext;
		_prev = pairPrev;
	}
	
	@SuppressWarnings("unchecked")
	public void swapRight() {
		if(_next==null) return;
		
		T pairPrev = _prev;
		T pairNext = _next._next;
		
		// Swap `right` and this node
		_next._next = (T) this;
		_prev = _next;
		
		// Put `_next` and this node back into the chain
		_next._prev = pairPrev;
		_next = pairNext;
	}
	
	public void replace(T target) {
		if(!poppedOut()) popOut();
		
		T p = target._prev;
		T n = target._next;
		
		// Remove `target` from the chain and put this node into it
		target._prev = target._next = null;
		_prev = p;
		_next = n;
	}
}
