package org.jLOAF.preprocessing.filter.featureSelection;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.inputs.StateBasedInput;
import org.jLOAF.preprocessing.filter.CaseBaseFilter;
import org.jLOAF.sim.complex.WeightedMean;
import org.jLOAF.weights.SimilarityWeights;
/**
 * this class checks the weights of the features of all the cases, when a weight of a feature is zero, it removes it from the list of the features
 * that the cases have.(here by features we mean inputs). this is only applicable when the similarityMetricStrategy used for the complexInput is 
 * the weightedMean.
 * @author Ibrahim Ali Fawaz
 *
 */
public class WeightsSeperatorFilter extends CaseBaseFilter {

	public WeightsSeperatorFilter(CaseBaseFilter f) {
		super(f);
		
	}

	@Override
	public CaseBase filter(CaseBase initial) {
		if(filter!=null){
			initial=filter.filter(initial);
		}
		Input i=null;
		for(Case c:initial.getCases()){
		 i= ((StateBasedInput)c.getInput()).getInput();
			break;
		}
		
		ComplexInput i1 =null;
			try{
				 i1= (ComplexInput)i;	
			}catch(ClassCastException e){
				System.out.println(e + "first");
				return initial;
			}
			
			if(i1.getSimilarityMetricStrategy() instanceof WeightedMean){
				SimilarityWeights sim =((WeightedMean)i1.getSimilarityMetricStrategy()).getSimilarityWeights();
			for(Case c:initial.getCases()){
				ComplexInput i2 =null;
				try{
					i2=(ComplexInput)((StateBasedInput)c.getInput()).getInput();;
				}catch (ClassCastException e){
					System.out.println(e);
					return initial;
				}
				for(String w:i2.getChildNames()){
					if(sim.getWeight(w)==0){
						i2.getChildren().remove(w);
					}
				}
				
			}
		}
		
		return initial;
	}

}
