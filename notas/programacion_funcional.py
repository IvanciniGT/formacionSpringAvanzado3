
def saluda(nombre):
    print("Hola " + nombre)

saluda("Ivan")

miFuncion = saluda          # Tengo una variable que punta a una funcion
miFuncion("Menchu")         # Ejecuto la función desde la variable

saluda("Menchu")


def generar_saludo_informal(nombre):
    return "Hola " + nombre

def generar_saludo_formal(nombre):
    return "Buenos días Don/Doña " + nombre

def imprimir_saludo(funcion_generadora_de_saludos, nombre):
    print(funcion_generadora_de_saludos(nombre))
    
imprimir_saludo(generar_saludo_formal, "Federico")

imprimir_saludo(generar_saludo_formal("Federico"))
