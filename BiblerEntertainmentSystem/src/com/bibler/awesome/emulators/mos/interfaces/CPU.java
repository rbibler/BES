package com.bibler.awesome.emulators.mos.interfaces;

public abstract interface CPU {
	
	public abstract int execute(int opCode);
	public abstract int fetch();
	public abstract void reset();
	public abstract void powerOn();

}
