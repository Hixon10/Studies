package ru.spbau.ayakomarov.drunkard.object;

/**
 *  for Objects whose move on field
 */

public interface IObjectMove {

    public void meetBarrier(Drunkard object);
    public void meetBarrier(Column object);
    public void meetBarrier(Bottle object);
    public void meetBarrier(Light object);
    public void meetBarrier(SectorPolice object);
    public void meetBarrier(Beggar object);
    public void meetBarrier(Policeman object);


}
