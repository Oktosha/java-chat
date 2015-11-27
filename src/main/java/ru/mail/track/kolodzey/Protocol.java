package ru.mail.track.kolodzey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.ProtocolException;

public class Protocol {
	public byte[] encode(Action action) throws ProtocolException {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.writeValueAsBytes(action);
		} catch(JsonProcessingException e) {
			throw new ProtocolException();
		}
	}

	public Action decode(byte[] encodedAction) throws ProtocolException {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.readValue(encodedAction, Action.class);
		} catch(IOException e) {
		 	throw new ProtocolException();
		}
	}
}