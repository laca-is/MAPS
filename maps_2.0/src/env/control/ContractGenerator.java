package control;

import java.text.DecimalFormat;
import java.util.Random;

import model.Contract;
import model.PSValues;

public class ContractGenerator {

	public static Random random = new Random();
	public static DecimalFormat df = new DecimalFormat("#.00");

	// statistic use (Sector 1 - 0.5, 0.5) (2 - 0.9 0.1) (3 - 0.1 0.9) (4 - 0.4
	// 0.6) (5 - 0.6 - 0.4)
	public static int cont = -1;

	public static Contract generateSectorContract(){
		Contract contract = null;
		//double wPrice, wTime;
//		wPrice = random.nextDouble();
//		wTime = 1 - wPrice;
//		return new Contract(Double.valueOf(df.format(wPrice)),Double.valueOf(df.format(wTime)));
		cont++;
		
		if(cont == 0)
			return new Contract(0.5, 0.5);
		else if(cont == 1)
			return new Contract(0.8, 0.2);
		else if(cont == 2)
			return new Contract(0.2, 0.8);
		else if(cont == 3)		
			return new Contract(0.6, 0.4);
		else if(cont == 4)		
			return new Contract(0.4, 0.6);
		
		return contract;
		
		
	}

	public static Contract generatePSContract() {

		int maxTime;
		double price;

		maxTime = random.nextInt(PSValues.MAX_TIME.getValue());
		price = random.nextDouble() * PSValues.MAX_PRICE.getValue();

		if (maxTime < PSValues.MIN_TIME.getValue())
			maxTime += PSValues.MIN_TIME.getValue();

		if (price < PSValues.MIN_PRICE.getValue())
			price += PSValues.MIN_PRICE.getValue();

		return new Contract(Double.valueOf(df.format(price)), maxTime);

	}

	public static void setContract() {

	}

}
