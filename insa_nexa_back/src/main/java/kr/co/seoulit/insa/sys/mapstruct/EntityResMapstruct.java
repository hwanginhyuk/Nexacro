package kr.co.seoulit.insa.sys.mapstruct;

import java.util.ArrayList;
import java.util.List;

public interface EntityResMapstruct<E,D> {
    D toDto(E e);
    List<D> toDto(List<E> e);
    ArrayList<D> toDto(ArrayList<E> e);

}
