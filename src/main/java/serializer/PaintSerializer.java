package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;
import java.io.IOException;

public class PaintSerializer extends JsonSerializer<Paint> {
    @Override
    public void serialize(Paint paint, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (paint instanceof Color) {
            // If it's a Color, serialize as an RGB integer value
            int rgb = ((Color) paint).getRGB();
            jsonGenerator.writeNumber(rgb);
        } else {
            // Handle other cases or throw an exception if needed
            throw new UnsupportedOperationException("Unsupported Paint type: " + paint.getClass().getName());
        }
    }
}
