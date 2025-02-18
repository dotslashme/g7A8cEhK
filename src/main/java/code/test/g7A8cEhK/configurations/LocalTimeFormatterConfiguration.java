package code.test.g7A8cEhK.configurations;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;

@Configuration

public class LocalTimeFormatterConfiguration {
    private static final String TIME_STRING = "HH:mm";

    @Bean
    @ConditionalOnProperty(value = "spring.jackson.date-format", matchIfMissing = true, havingValue = "none")
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder builder) {
                builder.simpleDateFormat(TIME_STRING);
                builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_STRING)));
                builder.deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_STRING)));
            }
        };
    }
}
