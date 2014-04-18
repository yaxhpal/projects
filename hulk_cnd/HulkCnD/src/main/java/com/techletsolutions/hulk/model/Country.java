package com.techletsolutions.hulk.model;

import java.util.Set;
import java.util.TreeSet;

import com.techletsolutions.db.EntityManager;

public class Country  extends CnDEntity {
	
protected Set<State> states;
	
	public Country (String name, Long parent) {
		this.id = 1238L;
		this.group_name = "Country";
		this.name = name;
		this.description = name;
		this.parent = parent;
		this.option1 = null;
		this.option2 = null;
		this.deleted = 'N';
		states = new TreeSet<State>();
	}
	
	public  Set<State> getAllStates() {
		return states;
	}
	
	public void addState(State state) {
		State thisState = getState(state.getName());
		if (thisState == null) {
			states.add(state);
		} else {
			for (City city : state.getAllCities()) {
				thisState.addCity(city);
			}
		}
	}
	
	public State getState(String stateName) {
		for (State state : states) {
			if (state.getName().equalsIgnoreCase(stateName)){
				return state;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString()+"\n");
		for (State state : states) {
			stringBuilder.append("\t"+state.toString()+"\n");
		}
		return stringBuilder.toString();
	}
	
	@Override
	public Long save(EntityManager em) {
		for (State state : states) {
			state.setParent(id);
			state.save(em);
		}
		return id;
	}
}