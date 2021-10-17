package org.wat.zad.intefaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AgentInterface extends Remote {
    double add(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
}
