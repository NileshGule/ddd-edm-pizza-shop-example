package com.mattstine.dddworkshop.pizzashop.kitchen;

import com.mattstine.dddworkshop.pizzashop.infrastructure.events.ports.EventLog;
import com.mattstine.dddworkshop.pizzashop.infrastructure.events.ports.Topic;
import com.mattstine.dddworkshop.pizzashop.ordering.OnlineOrderRef;
import com.mattstine.dddworkshop.pizzashop.ordering.OrderingService;
import lombok.Value;

import java.util.Set;

@Value
final class DefaultKitchenService implements KitchenService {
	EventLog eventLog;
	KitchenOrderRepository kitchenOrderRepository;
	PizzaRepository pizzaRepository;
	OrderingService orderingService;

	DefaultKitchenService(EventLog eventLog, KitchenOrderRepository kitchenOrderRepository, PizzaRepository pizzaRepository, OrderingService orderingService) {
		this.kitchenOrderRepository = kitchenOrderRepository;
		this.eventLog = eventLog;
		this.pizzaRepository = pizzaRepository;
		this.orderingService = orderingService;

		eventLog.subscribe(new Topic("kitchen_orders"), e ->{

		});

		eventLog.subscribe(new Topic("ordering"), e -> {

		});

		eventLog.subscribe(new Topic("pizzas"), e -> {

		});
	}

	@Override
	public void startOrderPrep(KitchenOrderRef kitchenOrderRef) {
		KitchenOrder kitchenOrder = kitchenOrderRepository.findByRef(kitchenOrderRef);
		kitchenOrder.startPrep();
	}

	@Override
	public void finishPizzaPrep(PizzaRef ref) {
		Pizza pizza = pizzaRepository.findByRef(ref);
		pizza.finishPrep();
	}

	@Override
	public void removePizzaFromOven(PizzaRef ref) {
		Pizza pizza = pizzaRepository.findByRef(ref);
		pizza.finishBake();
	}

	@Override
	public KitchenOrder findKitchenOrderByRef(KitchenOrderRef kitchenOrderRef) {
		return kitchenOrderRepository.findByRef(kitchenOrderRef);
	}

	@Override
	public KitchenOrder findKitchenOrderByOnlineOrderRef(OnlineOrderRef onlineOrderRef) {
		return kitchenOrderRepository.findByOnlineOrderRef(onlineOrderRef);
	}

	@Override
	public Pizza findPizzaByRef(PizzaRef ref) {
		return pizzaRepository.findByRef(ref);
	}

	@Override
	public Set<Pizza> findPizzasByKitchenOrderRef(KitchenOrderRef kitchenOrderRef) {
		return pizzaRepository.findPizzasByKitchenOrderRef(kitchenOrderRef);
	}

}
