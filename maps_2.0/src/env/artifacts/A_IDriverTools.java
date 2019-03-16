// CArtAgO artifact code for project maps_holo

package artifacts;

import java.text.DecimalFormat;

import cartago.*;
import model.PSValues;

public class A_IDriverTools extends Artifact {

	DecimalFormat df = new DecimalFormat("#.00");

	void init() {

	}

	@OPERATION
	public void calculateIDriverUtility(double wPrice, double wTime, double idPrice, int idTime, double offerPrice,
			int offerTime, OpFeedbackParam<Double> idUtility) {
		double utilityPrice, utilityTime, utility;
		double numeratorPrice;
		double numeratorTime;

		numeratorPrice = offerPrice - idPrice;
		numeratorTime = (double) (idTime - offerTime);

		if (numeratorPrice < 0)
			numeratorPrice = 0.0;
		if (numeratorTime < 0)
			numeratorTime = 0.0;

		utilityPrice = (numeratorPrice / (PSValues.MAX_PRICE.getValue() - PSValues.MIN_PRICE.getValue())) * wPrice;
		utilityTime = (numeratorTime / (PSValues.MAX_TIME.getValue() - PSValues.MIN_TIME.getValue())) * wTime;
		utility = 1 - (utilityPrice + utilityTime);
		idUtility.set(Double.valueOf(df.format(utility)));
	}
}
