package base.domain;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * @author who
 */
public interface Nameable<ID extends Serializable> extends Persistable<ID> {

    String getName();
}
