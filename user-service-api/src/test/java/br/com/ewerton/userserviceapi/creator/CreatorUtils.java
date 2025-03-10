package br.com.ewerton.userserviceapi.creator;


import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class CreatorUtils {

    private static final PodamFactory FACTORY = new PodamFactoryImpl();

    public static <T> T generateMock(final Class<T> clazz) {
        return FACTORY.manufacturePojo(clazz);
    }
}
