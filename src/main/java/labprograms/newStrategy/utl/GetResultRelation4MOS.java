package labprograms.newStrategy.utl;

import labprograms.MOS.sourceCode.MSR;

/**
 * @Description:
 * @auther phantom
 * @create 2019-09-23 上午11:17
 */
public class GetResultRelation4MOS {


    public static String getResultRelation(MSR sourceResult, MSR followUpResult) {
        String resultRelation = "";
        if (sourceResult.numberOfMealsForCrewMembers == followUpResult.numberOfMealsForCrewMembers) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfMealsForCrewMembers > followUpResult.numberOfMealsForCrewMembers) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfMealsForPilots == followUpResult.numberOfMealsForPilots) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfMealsForPilots > followUpResult.numberOfMealsForPilots) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfChildMeals == followUpResult.numberOfChildMeals) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfChildMeals > followUpResult.numberOfChildMeals) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfBundlesOfFlowers == followUpResult.numberOfBundlesOfFlowers) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfBundlesOfFlowers > followUpResult.numberOfBundlesOfFlowers) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfFirstClassMeals == followUpResult.numberOfFirstClassMeals) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfFirstClassMeals > followUpResult.numberOfFirstClassMeals) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfBusinessClassMeals == followUpResult.numberOfBusinessClassMeals) {
            resultRelation += "do not change;";
        } else if (sourceResult.numberOfBusinessClassMeals > followUpResult.numberOfBusinessClassMeals) {
            resultRelation += "decrease;";
        } else {
            resultRelation += "increase;";
        }

        if (sourceResult.numberOfEconomicClassMeals == followUpResult.numberOfEconomicClassMeals) {
            resultRelation += "do not change";
        } else if (sourceResult.numberOfEconomicClassMeals > followUpResult.numberOfEconomicClassMeals) {
            resultRelation += "decrease";
        } else {
            resultRelation += "increase";
        }
        return resultRelation;
    }
}
