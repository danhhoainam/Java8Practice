package predicates;

import models.Trader;

import java.util.function.BiPredicate;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class TraderPredicates {

	public static BiPredicate<Trader, String> filterTraderByCityName = (trader, cityName) -> cityName.equals(trader.getCity());
}
