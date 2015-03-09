package library.DateUtil;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * Adapter to convert between to LocalDate and the ISO 8601
 * @author Jiakuan
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
