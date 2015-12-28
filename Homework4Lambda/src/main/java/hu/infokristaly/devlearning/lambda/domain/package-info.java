@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class, type = LocalDate.class)
})
package hu.infokristaly.devlearning.lambda.domain;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
