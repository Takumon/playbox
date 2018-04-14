package org.stairwaybook.simulation

abstract class BasicCircuitSimulation extends Simulation {
  def InverterDelay: Int

  def AndGateDelay: Int

  def OrGateDeleay: Int


  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()
    def getSignal = sigVal
    def setSignal(s: Boolean) =
      if(s != sigVal) {
        sigVal = s
        actions foreach (_ ())
      }

    def addAction(a: Action) = {
      actions = a:: actions
      a()
    }
  }

  def inverter(input: Wire, output: Wire): Unit =  {
    def inverterAction() = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction inverterAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def andAction() = {
      val sig = a1.getSignal & a2.getSignal

      afterDelay(AndGateDelay) {
        output setSignal sig
      }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  def orGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def orAction() = {
      val sig = a1.getSignal | a2.getSignal

      afterDelay(OrGateDeleay) {
        output setSignal sig
      }
    }

    a1 addAction orAction
    a2 addAction orAction
  }

  def probe(name: String, wire: Wire) = {
    def probeAction() = println(s"$name $currentTime new-value = ${wire.getSignal}")
    wire addAction probeAction
  }
}