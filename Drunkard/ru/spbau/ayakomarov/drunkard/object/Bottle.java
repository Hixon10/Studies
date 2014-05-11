package ru.spbau.ayakomarov.drunkard.object;

public class Bottle extends ObjectInCell {

    @Override
    public char view() {
        return 'B';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }
}
