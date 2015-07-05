package com.techobyte.j2sample.model;

public class Hello {

	private String message = "Hello ";
	public Hello() {
		setMessage("Hello ");
	}
	
	public Hello(String to) {
		setMessage(getMessage() + to);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Hello [message=" + message + "]";
	}

}
