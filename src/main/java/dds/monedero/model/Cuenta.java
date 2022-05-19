package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuenta {

  private Double saldo = 0d;
  private List<Movimiento> movimientos = new ArrayList<>();
  private Double limiteDiarioExtraccion = 1000d;
  private Integer limiteDiarioDeposito = 3;

  public Cuenta() {
    saldo = 0d;
  }

  public Cuenta(Double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(Double unMonto) {
    this.validarMonto(unMonto);
    this.validarLimiteDepositosDiarios();
    this.setSaldo(this.getSaldo() + unMonto);
    this.agregarMovimiento(LocalDate.now(), unMonto, true);
  }

  public void sacar(Double unMonto) {
    this.validarMonto(unMonto);
    this.validarSaldoSuficiente(unMonto);
    this.validarLimiteExtraccionDiario(unMonto);
    this.setSaldo(this.getSaldo() - unMonto);
    this.agregarMovimiento(LocalDate.now(), unMonto, false);
  }

  private void validarMonto(Double unMonto) {
    if(unMonto < 0d) {
      throw new MontoNegativoException(
          unMonto.toString() + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private void validarLimiteDepositosDiarios() {
    List<Movimiento> depositosDeHoy =
        this.movimientos
            .stream()
            .filter(mov -> mov.fueDepositado(LocalDate.now()))
            .collect(Collectors.toList());

    if (depositosDeHoy.size() >= this.limiteDiarioDeposito) {
      throw new MaximaCantidadDepositosException(
          "Ya excedio los " + this.limiteDiarioDeposito + " depositos diarios");
    }
  }

  private void validarSaldoSuficiente(Double unMonto) {
    if(unMonto > this.getSaldo()) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  private void validarLimiteExtraccionDiario(Double unMonto) {
    Double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    Double montoRestanteHoy = this.limiteDiarioExtraccion - montoExtraidoHoy;

    if (unMonto > montoRestanteHoy) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + this.limiteDiarioExtraccion.toString() + " diarios, " +
          "restante: " + montoRestanteHoy);
    }
  }

  private void agregarMovimiento(LocalDate fecha, Double cuanto, Boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public Double getMontoExtraidoA(LocalDate unaFecha) {
    return getMovimientos().stream()
        .filter(mov -> mov.fueExtraido(unaFecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

}
