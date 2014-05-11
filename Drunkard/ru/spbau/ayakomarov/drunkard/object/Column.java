package ru.spbau.ayakomarov.drunkard.object;


public class Column extends ObjectInCell {

    @Override
    public char view() {
        return 'C';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }
}
