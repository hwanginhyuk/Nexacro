package kr.co.seoulit.insa.sys.mapstruct;


import java.util.ArrayList;
import java.util.List;

public interface EntityReqMapstrut<E,D> {
    E toEntity(D d);
    List<E> toEntity(List<D> d);
    ArrayList<E> toEntity(ArrayList<D> d);
}
