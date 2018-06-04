package fr.laposte.bscc.pi_premiss.service;

import org.springframework.data.domain.Page;

public interface IOperations<T> {

    public Page<T> findPaginated(final int page, final int size);

}