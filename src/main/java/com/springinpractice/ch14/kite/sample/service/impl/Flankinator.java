package com.springinpractice.ch14.kite.sample.service.impl;

import org.springframework.stereotype.Component;

@Component
public class Flankinator {
	private volatile boolean up = true;
	
	public void simulateFlakiness() {
		if (up) {
			if (Math.random() < 0.05) {
				this.up = false;
			}
		}  else {
			if (Math.random() < 0.2) {
				this.up = true;
			}
		}
		if (!up) {
			throw new RuntimeException("Ops, service down");				
		}
	}
}
