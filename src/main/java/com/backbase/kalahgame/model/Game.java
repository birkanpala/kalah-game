package com.backbase.kalahgame.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.function.Supplier;

import static com.backbase.kalahgame.model.Status.*;

@Entity
@Getter
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private Status status;

    private final Board board;

    @Setter
    @Transient
    private Player player;

    /**
     * Creates a new game
     */
    public Game() {
        this.status = CREATED;
        board = new Board();
    }

    /**
     * Starts game with the player.
     */
    public void start() {

        if (this.status != CREATED) {
            throw new IllegalStateException("Game is already started or finished!");
        }

        this.status = byPlayer(this.player);
    }

    /**
     * Plays the given function and assigns the status.
     *
     * @param playFunction Play function returning the status
     */
    public void play(Supplier<Status> playFunction) {
        this.status = playFunction.get();
    }

    /**
     * Checks the game is finished or not.
     *
     * @return {@code true} if the game is finished, otherwise {@code false}
     */
    public boolean isFinished() {
        return this.status == FINISHED;
    }

    /**
     * Finishes the game.
     */
    public void finish() {
        this.board.collectSeeds();
        this.status = FINISHED;
    }

}
