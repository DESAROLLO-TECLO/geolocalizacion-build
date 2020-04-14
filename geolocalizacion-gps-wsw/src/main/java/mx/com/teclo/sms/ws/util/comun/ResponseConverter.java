package mx.com.teclo.sms.ws.util.comun;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

public class ResponseConverter<S, T> {

	public static <T, U> ArrayList<U> converterLista(final Object target, final List<T> source, final Class<U> destType) {

		final Mapper mapper = new DozerBeanMapper();
		mapper.map(source, target);
		
		final ArrayList<U> dest = new ArrayList<U>();

		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(mapper.map(element, destType));
		}
		List s1 = new ArrayList();
		s1.add(null);
		dest.removeAll(s1);

		return dest;
	}
	
	public static void copiarPropriedades(Object beanDestino, Object beanOrigen) {
		BeanUtils.copyProperties(beanOrigen, beanDestino);
	}
	
	public static <O, D> D copiarPropiedadesFull(O  originalObject, Class<D> destinationObject) {
        return parser(destinationObject, originalObject);
    }
	
	private static <D> D parser(Class<D> destinationObject, Object originalObject) {
		Mapper mapper = new DozerBeanMapper();
        return mapper.map(originalObject, destinationObject);
    }
}
