/**
 * FILE: KnnJudgement.java
 * PATH: org.datasyslab.geospark.knnJudgement.KnnJudgement.java
 * Copyright (c) 2015-2017 GeoSpark Development Team
 * All rights reserved.
 */
package org.datasyslab.geospark.knnJudgement;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;



// TODO: Auto-generated Javadoc
/**
 * The Class GeometryKnnJudgement.
 */
public class KnnJudgement<T extends Geometry> implements FlatMapFunction<Iterator<T>, Object>, Serializable{
	
	/** The k. */
	int k;
	
	/** The query center. */
	Point queryCenter;
	
	/**
	 * Instantiates a new geometry knn judgement.
	 *
	 * @param queryCenter the query center
	 * @param k the k
	 */
	public KnnJudgement(Point queryCenter,int k)
	{
		this.queryCenter=queryCenter;
		this.k=k;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.spark.api.java.function.FlatMapFunction#call(java.lang.Object)
	 */
	@Override
	public Iterator<Object> call(Iterator<T> input) throws Exception {
		PriorityQueue<Object> pq = new PriorityQueue<Object>(k, new GeometryDistanceComparator(queryCenter,false));
		while (input.hasNext()) {
			if (pq.size() < k) {
				pq.offer(input.next());
			} else {
				Geometry curpoint = input.next();
				double distance = curpoint.distance(queryCenter);
				double largestDistanceInPriQueue = ((Geometry) pq.peek()).distance(queryCenter);
				if (largestDistanceInPriQueue > distance) {
					pq.poll();
					pq.offer(curpoint);
				}
			}
		}
		ArrayList<Object> res = new ArrayList<Object>();
		for (int i = 0; i < k; i++) {
			res.add(pq.poll());
		}
		return res.iterator();
	}

}
