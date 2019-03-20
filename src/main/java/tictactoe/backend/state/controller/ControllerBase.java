package tictactoe.backend.state.controller;

import lombok.Getter;
import lombok.Setter;
import tictactoe.backend.state.listener.GlobalStateChangeHandler;

@Getter @Setter
public abstract class ControllerBase {
    GlobalStateChangeHandler globalStateChangeHandler;
}
