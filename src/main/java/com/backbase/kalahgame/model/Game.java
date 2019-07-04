package com.backbase.kalahgame.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private Status status;

    private Board board;

    public Game() {
        this.status = Status.CREATED;
        board = new Board();
    }

}
