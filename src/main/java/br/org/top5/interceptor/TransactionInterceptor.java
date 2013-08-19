package br.org.top5.interceptor;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.org.top5.annotation.Transactional;

@Intercepts
public class TransactionInterceptor implements Interceptor {
	private final EntityManager entityManager;

	public TransactionInterceptor(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object instance) {
		try {
			entityManager.getTransaction().begin();
			stack.next(method, instance);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
		}
	}

	public boolean accepts(ResourceMethod method) {
		return method.getMethod().isAnnotationPresent(Transactional.class)
				|| method.getResource().getType()
						.isAnnotationPresent(Transactional.class);
	}
}