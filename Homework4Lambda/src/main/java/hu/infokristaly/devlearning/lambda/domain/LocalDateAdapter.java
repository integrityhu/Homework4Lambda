package hu.infokristaly.devlearning.lambda.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{

		@Override
	    public LocalDate unmarshal(String v) throws Exception
	    {
			LocalDate result = LocalDate.parse(v, formatter);
	        return result;
	    }

	    @Override
	    public String marshal(LocalDate v) throws Exception
	    {
	    	String result = v.format(formatter); 
	        return result;
	    }

	    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	      .appendPattern("yyyy")
	      .appendLiteral(".")
	      .appendPattern("MM")
	      .appendLiteral(".")
	      .appendPattern("dd")
	      .toFormatter();
}
