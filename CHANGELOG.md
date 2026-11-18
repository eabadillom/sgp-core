# Changelog

Todos los cambios importantes en este proyecto serán documentados en este archivo.

Este proyecto sigue versionado semántico (SemVer) y el formato de [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/).

---

## [0.2.0] - 2026-11-17
### Added 
* En DiaNoLaborableBL se agrega:
    * esDiaNoLaboral(Date fecha, String pais), que determina si una fecha corresponde a un día no laborable en el país especificado.
* En EmpleadoBL se agrega:
    * asignarHorarioDeEmpleadoARegistro(Empleado empleado, Date horaInicio, Date horaFin), que asigna las horas de entrada y salida configuradas para el empleado. Si no cuenta con alguna de ellas, establece horarios predeterminados: 07:00 para la entrada y 16:00 para la salida.
    * getDiasDescansoPorEmpleado(List<String> diasLaborables), que determina y devuelve los días de descanso del empleado a partir de los días de la semana y los días laborables establecidos para este.
    * crearAsistencia(Empleado empleado, EstatusAsistencia estatusAsistencia, Date horaInicio, Date horaFin), que crea y devuelve un objeto Asistencia con la información del empleado, el estatus de asistencia y los horarios de entrada y salida proporcionados.
    * generarDiaNoLaboral(Empleado empleado), que valida si el día corresponde a un día no laborable para el empleado y, de ser así, crea y guarda el registro de asistencia correspondiente.
    * generarDiaDescanso(Empleado empleado), que determina si el día corresponde a un día de descanso del empleado y, de ser así, crea y guarda el registro de asistencia correspondiente, considerando los días de descanso establecidos individualmente para cada empleado.
    

## [0.1.1] - 2026-04-16

### Added
* Se agregan archivos de documentación del proyecto (README.md, CHANGELOG.md y LICENSE).

### Changed
* Se actualiza dependencia de `mysql-connector-j` de `8.0.33` a `8.4.0`.
* Se actualiza dependencia `sgp-tools` de la versión `0.0.1` a la `0.0.2`.

### Maintenance
- **CI/CD:** Implementado pipeline automatizado de compilación y despliegue a GitHub Packages.

## Nota
Los cambios previos a esta versión (desde el inicio del proyecto) no están documentados por tratarse de la fase inicial de desarrollo.
