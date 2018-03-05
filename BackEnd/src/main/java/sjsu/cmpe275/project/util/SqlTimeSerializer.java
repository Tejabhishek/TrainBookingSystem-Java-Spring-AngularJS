package sjsu.cmpe275.project.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by shiva on 12/18/17.
 */
public class SqlTimeSerializer extends JsonSerializer<Time> {

    @Override
    public void serialize(Time time, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateString = sdf.format(time);
       // String formattedDate = dateFormat.format(date);

        gen.writeString(dateString);

    }

}
