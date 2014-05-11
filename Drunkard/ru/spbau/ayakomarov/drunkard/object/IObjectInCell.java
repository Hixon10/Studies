package ru.spbau.ayakomarov.drunkard.object;

/**
 *  for Objects whose locate on field  in cells
 */

public interface IObjectInCell {
    public char view();
    public void reactionBarrier(IObjectMove object);
}
