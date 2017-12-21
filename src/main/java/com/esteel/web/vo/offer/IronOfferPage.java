package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.List;

public class IronOfferPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns the number of total pages.
	 */
	private int totalPages;

	/**
	 * Returns the total amount of elements.
	 */
	private int totalElements;
	
	/**
	 * Returns the number of the current Slice. Is always non-negative.
	 */
	private int number;

	/**
	 * Returns the size of the Slice.
	 */
	private int size;

	/**
	 * Returns the number of elements currently on this Slice.
	 */
	private int numberOfElements;

	/**
	 * Returns the page content as Slice.
	 */
	private List<IronOfferResponse> content;


	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public List<IronOfferResponse> getContent() {
		return content;
	}

	public void setContent(List<IronOfferResponse> content) {
		this.content = content;
	}
}
