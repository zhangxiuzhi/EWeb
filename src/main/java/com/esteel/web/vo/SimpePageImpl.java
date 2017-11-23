package com.esteel.web.vo;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-23
 * Time: 17:52
 */
public class SimpePageImpl<T> extends PageImpl<T>{

    private int totalPages;
    private long totalElements;
    private boolean last;
    private int number;
    private int size;
    private long total;
    private boolean first;
    private int numberOfElements;


    public SimpePageImpl(){
        this(new ArrayList<T>());
    }

    public SimpePageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SimpePageImpl(List<T> content) {
        super(content);
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        this.total = totalElements;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    @Override
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Pageable getPageable(){
        return new PageRequest(this.number,this.size);
    }
}
