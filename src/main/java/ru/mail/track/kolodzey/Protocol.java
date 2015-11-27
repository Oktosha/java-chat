package ru.mail.track.kolodzey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

import java.io.IOException;
import java.net.ProtocolException;

public class Protocol {
	public byte[] encode(NetData netData) throws ProtocolException {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.writeValueAsBytes(netData);
		} catch(JsonProcessingException e) {
			throw new ProtocolException();
		}
	}

	public NetData decode(byte[] encodedData) throws ProtocolException {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.readValue(encodedData, NetData.class);
		} catch(IOException e) {
		 	throw new ProtocolException();
		}
	}
}