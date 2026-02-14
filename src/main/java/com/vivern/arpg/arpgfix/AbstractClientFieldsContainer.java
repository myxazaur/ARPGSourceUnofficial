package com.vivern.arpg.arpgfix;

/**
 * Нечистый приёмчик, чтобы хранить клиентские классы в static-полях и не ловить краш мода
 * при загрузке классов с такими полями на сервере.
 * By PurplePrint
 */
public abstract class AbstractClientFieldsContainer implements IFieldInit {
    protected AbstractClientFieldsContainer() {
        this.initFields();
    }
}