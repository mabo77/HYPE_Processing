package hype.event;

import hype.behavior.HBehavior;
import hype.util.HCallback;
import processing.core.PApplet;


public class HTimer extends HBehavior {
	public HCallback _callback;
	protected float _intervalCounter;
	protected int _interval, _cycleCounter, _numCycles;
	protected boolean _usesFrames;
	
	public HTimer() {
		_interval = 1000;
	}
	
	public HTimer(int timerInterval) {
		_interval = timerInterval;
	}
	
	public HTimer(int timerInterval, int numberOfCycles) {
		_interval = timerInterval;
		_numCycles = numberOfCycles;
	}
	
	public HTimer callback(HCallback cb) {
		if(cb == null) unregister();
		else register();
		
		_callback = cb;
		return this;
	}
	
	public HCallback callback() {
		return _callback;
	}
	
	public HTimer interval(int i) {
		_interval = i;
		return this;
	}
	
	public int interval() {
		return _interval;
	}
	
	public HTimer cycleCounter(int cycleIndex) {
		_cycleCounter = cycleIndex;
		return this;
	}
	
	public int cycleCounter() {
		return _cycleCounter;
	}
	
	public HTimer numCycles(int cycles) {
		_numCycles = cycles;
		return this;
	}
	
	public int numCycles() {
		return _numCycles;
	}
	
	public HTimer cycleIndefinitely() {
		_numCycles = 0;
		return this;
	}
	
	public HTimer useMillis() {
		_usesFrames = false;
		return this;
	}
	
	public boolean usesMillis() {
		return !_usesFrames;
	}
	
	public HTimer useFrames() {
		_usesFrames = true;
		return this;
	}
	
	public boolean usesFrames() {
		return _usesFrames;
	}
	
	@Override
	public void runBehavior(PApplet app) {
		if(_intervalCounter < _interval) {
			_intervalCounter += (_usesFrames)? 1 : 1000/app.frameRate;
		} else {
			_intervalCounter = 0;
			
			if(_callback != null) _callback.run(_cycleCounter);
			
			if(_numCycles > 0 && ++_cycleCounter >= _numCycles) unregister();
		}
	}
	
	@Override
	public HTimer register() {
		return (HTimer) super.register();
	}
	
	@Override
	public HTimer unregister() {
		_numCycles = 0;
		_intervalCounter = 0;
		return (HTimer) super.unregister();
	}
}
