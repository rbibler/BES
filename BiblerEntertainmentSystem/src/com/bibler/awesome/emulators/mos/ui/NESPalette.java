package com.bibler.awesome.emulators.mos.ui;

import java.awt.Color;

public class NESPalette {
	
	private final static int[] NES_PALETTE = new int[] {palColor(124,124,124), 
			palColor(0,0,252),
			palColor(0,0,188),
			palColor(68,40,188),
			palColor(148,0,132),
			palColor(168,0,32),
			palColor(168,16,0),
			palColor(136,20,0),
			palColor(80,48,0),
			palColor(0,120,0),
			palColor(0,104,0),
			palColor(0,88,0),
			palColor(0,64,88),
			palColor(0,0,0),
			palColor(0,0,0),
			palColor(0,0,0),
			palColor(188,188,188),
			palColor(0,120,248),
			palColor(0,88,248),
			palColor(104,68,252),
			palColor(216,0,204),
			palColor(228,0,88),
			palColor(248,56,0),
			palColor(228,92,16),
			palColor(172,124,0),
			palColor(0,184,0),
			palColor(0,168,0),
			palColor(0,168,68),
			palColor(0,136,136),
			palColor(0,0,0),
			palColor(0,0,0),
			palColor(0,0,0),
			palColor(248,248,248),
			palColor(60,188,252),
			palColor(104,136,252),
			palColor(152,120,248),
			palColor(248,120,248),
			palColor(248,88,152),
			palColor(248,120,88),
			palColor(252,160,68),
			palColor(248,184,0),
			palColor(184,248,24),
			palColor(88,216,84),
			palColor(88,248,152),
			palColor(0,232,216),
			palColor(120,120,120),
			palColor(0,0,0),
			palColor(0,0,0),
			palColor(252,252,252),
			palColor(164,228,252),
			palColor(184,184,248),
			palColor(216,184,248),
			palColor(248,184,248),
			palColor(248,164,192),
			palColor(240,208,176),
			palColor(252,224,168),
			palColor(248,216,120),
			palColor(216,248,120),
			palColor(184,248,184),
			palColor(184,248,216),
			palColor(0,252,252),
			palColor(248,216,248),
			palColor(0,0,0),
			palColor(0,0,0),};
	
	public static int grabValue(int value) {
		return NES_PALETTE[value];
	}
	
	public static Color getColor(int value) {
		int color = NES_PALETTE[value];
		return new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
	}
	
	private static int palColor(int r, int g, int b) {
		return 0xFF << 24 | r << 16 | g << 8 | b;
	}

}
