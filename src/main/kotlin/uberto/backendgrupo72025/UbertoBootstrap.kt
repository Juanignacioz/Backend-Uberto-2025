package uberto.backendgrupo72025

import uberto.backendgrupo72025.domain.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import uberto.backendgrupo72025.domain.neo4j.ConductorNode
import uberto.backendgrupo72025.domain.neo4j.ViajeroNode
import uberto.backendgrupo72025.repository.jpa.*
import uberto.backendgrupo72025.repository.mongo.*
import uberto.backendgrupo72025.repository.neo4j.ConductorNodeRepository
import uberto.backendgrupo72025.repository.neo4j.ViajeroNodeRepository
import java.time.LocalDateTime

@Component
class UbertoBootstrap(
    val viajeRepository: ViajeRepository,
    val comentarioRepository: ComentarioRepository,
    val viajeroRepository: ViajeroRepository,
    val conductorRepository: ConductorRepository,
    val dataViajeRepository: DataViajeRepository,
    val busquedaRepository: BusquedaRepository,
    val viajeroNodeRepository: ViajeroNodeRepository,
    val conductorNodeRepository: ConductorNodeRepository,
) : InitializingBean {

    @Transactional
    override fun afterPropertiesSet() {
        deleteAllData()
        crearUsuarios()
        crearChoferes()
        crearViajes()
        crearAmistades()
//      crearComentarios()
    }

    fun deleteAllData() {
        conductorRepository.deleteAll()
        dataViajeRepository.deleteAll()
        busquedaRepository.deleteAll()
        viajeroNodeRepository.deleteAll()
        conductorNodeRepository.deleteAll()
    }

    // VIAJEROS
    val viajero1 = Viajero(
        nombre = "Juan",
        apellido = "Pérez",
        edad = 28,
        username = "juanp",
        contrasenia = "pass123",
        telefono = 123456789,
        esChofer = false,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375420/yzn4herzts1biwnlpn9e.jpg",
        saldo = 1000000.0,
    )
    val viajero2 = Viajero(
        nombre = "María",
        apellido = "González",
        edad = 34,
        username = "mariag",
        contrasenia = "secure456",
        telefono = 987654321,
        esChofer = false,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109126/h0mmvlzszwfihmeb60tx.jpg",
        saldo = 23000000.75,
    )
    val viajero3 = Viajero(
        nombre = "Carlos",
        apellido = "López",
        edad = 23,
        username = "carlosl",
        contrasenia = "mypwd789",
        telefono = 456789123,
        esChofer = false,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1746746263/poktdygjjmdd8n04zedy.jpg",
        saldo = 8000000.25,
    )
    val viajero4 = Viajero(
        nombre = "Ana",
        apellido = "Martínez",
        edad = 41,
        username = "anam",
        contrasenia = "password1",
        telefono = 321654987,
        esChofer = false,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
        saldo = 3500.00,
    )


//    val viajero101 = Viajero(
//        nombre = "Pedro",
//        apellido = "Sánchez",
//        edad = 41,
//        username = "psánchez1",
//        contrasenia = "pass6961",
//        telefono = 309058038,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2032.87
//    )
//
//
//    val viajero102 = Viajero(
//        nombre = "Luis",
//        apellido = "Castro",
//        edad = 55,
//        username = "lcastro2",
//        contrasenia = "pass8660",
//        telefono = 318846206,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4281.47
//    )
//
//
//    val viajero103 = Viajero(
//        nombre = "Lucia",
//        apellido = "Fernández",
//        edad = 43,
//        username = "lfernández3",
//        contrasenia = "pass5562",
//        telefono = 306780382,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3204.12
//    )
//
//
//    val viajero104 = Viajero(
//        nombre = "Miguel",
//        apellido = "Castro",
//        edad = 46,
//        username = "mcastro4",
//        contrasenia = "pass2683",
//        telefono = 310236104,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4746.51
//    )
//
//
//    val viajero5 = Viajero(
//        nombre = "Luis",
//        apellido = "Moreno",
//        edad = 25,
//        username = "lmoreno5",
//        contrasenia = "pass2178",
//        telefono = 375099785,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4158.9
//    )
//
//
//    val viajero6 = Viajero(
//        nombre = "Elena",
//        apellido = "Ruiz",
//        edad = 40,
//        username = "eruiz6",
//        contrasenia = "pass8958",
//        telefono = 350871549,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3798.35
//    )
//
//
//    val viajero7 = Viajero(
//        nombre = "Sofia",
//        apellido = "Martínez",
//        edad = 35,
//        username = "smartínez7",
//        contrasenia = "pass5373",
//        telefono = 385757968,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1632.1
//    )
//
//
//    val viajero8 = Viajero(
//        nombre = "Javier",
//        apellido = "Fernández",
//        edad = 23,
//        username = "jfernández8",
//        contrasenia = "pass3469",
//        telefono = 385449908,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4747.51
//    )
//
//
//    val viajero9 = Viajero(
//        nombre = "Lucia",
//        apellido = "Moreno",
//        edad = 38,
//        username = "lmoreno9",
//        contrasenia = "pass1478",
//        telefono = 399845683,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2169.8
//    )
//
//
//    val viajero10 = Viajero(
//        nombre = "Javier",
//        apellido = "Martínez",
//        edad = 28,
//        username = "jmartínez10",
//        contrasenia = "pass5479",
//        telefono = 319374687,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2442.42
//    )
//
//
//    val viajero11 = Viajero(
//        nombre = "Lucia",
//        apellido = "Ramos",
//        edad = 43,
//        username = "lramos11",
//        contrasenia = "pass4033",
//        telefono = 384047624,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1556.68
//    )
//
//
//    val viajero12 = Viajero(
//        nombre = "Maria",
//        apellido = "Ruiz",
//        edad = 38,
//        username = "mruiz12",
//        contrasenia = "pass8827",
//        telefono = 394444847,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2740.08
//    )
//
//
//    val viajero13 = Viajero(
//        nombre = "Sofia",
//        apellido = "Castro",
//        edad = 58,
//        username = "scastro13",
//        contrasenia = "pass3446",
//        telefono = 393985997,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4252.8
//    )
//
//
//    val viajero14 = Viajero(
//        nombre = "Lucia",
//        apellido = "Martínez",
//        edad = 53,
//        username = "lmartínez14",
//        contrasenia = "pass2001",
//        telefono = 397954975,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3930.65
//    )
//
//
//    val viajero15 = Viajero(
//        nombre = "Luis",
//        apellido = "Torres",
//        edad = 18,
//        username = "ltorres15",
//        contrasenia = "pass9948",
//        telefono = 312642578,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1430.74
//    )
//
//
//    val viajero16 = Viajero(
//        nombre = "Maria",
//        apellido = "Fernández",
//        edad = 45,
//        username = "mfernández16",
//        contrasenia = "pass3878",
//        telefono = 359089957,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1057.5
//    )
//
//
//    val viajero17 = Viajero(
//        nombre = "Miguel",
//        apellido = "Martínez",
//        edad = 19,
//        username = "mmartínez17",
//        contrasenia = "pass5688",
//        telefono = 387934115,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3819.19
//    )
//
//
//    val viajero18 = Viajero(
//        nombre = "Lucia",
//        apellido = "Ruiz",
//        edad = 18,
//        username = "lruiz18",
//        contrasenia = "pass3316",
//        telefono = 323217345,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3137.17
//    )
//
//
//    val viajero19 = Viajero(
//        nombre = "Maria",
//        apellido = "Fernández",
//        edad = 33,
//        username = "mfernández19",
//        contrasenia = "pass2965",
//        telefono = 398115044,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3757.28
//    )
//
//
//    val viajero20 = Viajero(
//        nombre = "Luis",
//        apellido = "Ruiz",
//        edad = 58,
//        username = "lruiz20",
//        contrasenia = "pass9890",
//        telefono = 316716763,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1779.17
//    )
//
//
//    val viajero21 = Viajero(
//        nombre = "Elena",
//        apellido = "Fernández",
//        edad = 31,
//        username = "efernández21",
//        contrasenia = "pass2264",
//        telefono = 382395865,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2271.98
//    )
//
//
//    val viajero22 = Viajero(
//        nombre = "Elena",
//        apellido = "Díaz",
//        edad = 49,
//        username = "edíaz22",
//        contrasenia = "pass9484",
//        telefono = 392601626,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2296.13
//    )
//
//
//    val viajero23 = Viajero(
//        nombre = "Maria",
//        apellido = "Fernández",
//        edad = 56,
//        username = "mfernández23",
//        contrasenia = "pass5854",
//        telefono = 380049259,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1724.17
//    )
//
//
//    val viajero24 = Viajero(
//        nombre = "Ana",
//        apellido = "Díaz",
//        edad = 54,
//        username = "adíaz24",
//        contrasenia = "pass5102",
//        telefono = 396674033,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3666.18
//    )
//
//
//    val viajero25 = Viajero(
//        nombre = "Luis",
//        apellido = "Ruiz",
//        edad = 64,
//        username = "lruiz25",
//        contrasenia = "pass6604",
//        telefono = 319749355,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4692.07
//    )
//
//
//    val viajero26 = Viajero(
//        nombre = "Elena",
//        apellido = "Díaz",
//        edad = 37,
//        username = "edíaz26",
//        contrasenia = "pass9944",
//        telefono = 362796544,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3991.2
//    )
//
//
//    val viajero27 = Viajero(
//        nombre = "Sofia",
//        apellido = "Moreno",
//        edad = 51,
//        username = "smoreno27",
//        contrasenia = "pass5265",
//        telefono = 335324988,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3514.6
//    )
//
//
//    val viajero28 = Viajero(
//        nombre = "Luis",
//        apellido = "Martínez",
//        edad = 58,
//        username = "lmartínez28",
//        contrasenia = "pass3677",
//        telefono = 394668545,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4632.68
//    )
//
//
//    val viajero29 = Viajero(
//        nombre = "Javier",
//        apellido = "Ramos",
//        edad = 41,
//        username = "jramos29",
//        contrasenia = "pass5561",
//        telefono = 388409561,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2246.64
//    )
//
//
//    val viajero30 = Viajero(
//        nombre = "Luis",
//        apellido = "Díaz",
//        edad = 64,
//        username = "ldíaz30",
//        contrasenia = "pass1637",
//        telefono = 362899295,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2697.69
//    )
//
//
//    val viajero31 = Viajero(
//        nombre = "Carlos",
//        apellido = "Fernández",
//        edad = 28,
//        username = "cfernández31",
//        contrasenia = "pass6604",
//        telefono = 362302957,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1155.39
//    )
//
//
//    val viajero32 = Viajero(
//        nombre = "Elena",
//        apellido = "Ruiz",
//        edad = 35,
//        username = "eruiz32",
//        contrasenia = "pass6974",
//        telefono = 354598203,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4617.25
//    )
//
//
//    val viajero33 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramos",
//        edad = 40,
//        username = "mramos33",
//        contrasenia = "pass8634",
//        telefono = 391163169,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3120.82
//    )
//
//
//    val viajero34 = Viajero(
//        nombre = "Elena",
//        apellido = "Díaz",
//        edad = 43,
//        username = "edíaz34",
//        contrasenia = "pass8786",
//        telefono = 382558868,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4561.53
//    )
//
//
//    val viajero35 = Viajero(
//        nombre = "Sofia",
//        apellido = "Ruiz",
//        edad = 42,
//        username = "sruiz35",
//        contrasenia = "pass7221",
//        telefono = 311777447,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3980.13
//    )
//
//
//    val viajero36 = Viajero(
//        nombre = "Ana",
//        apellido = "Ramírez",
//        edad = 42,
//        username = "aramírez36",
//        contrasenia = "pass4563",
//        telefono = 326642568,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2962.1
//    )
//
//
//    val viajero37 = Viajero(
//        nombre = "Pedro",
//        apellido = "Castro",
//        edad = 51,
//        username = "pcastro37",
//        contrasenia = "pass1436",
//        telefono = 391497155,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1814.39
//    )
//
//
//    val viajero38 = Viajero(
//        nombre = "Maria",
//        apellido = "Moreno",
//        edad = 61,
//        username = "mmoreno38",
//        contrasenia = "pass9666",
//        telefono = 302911901,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2869.01
//    )
//
//
//    val viajero39 = Viajero(
//        nombre = "Maria",
//        apellido = "Ramírez",
//        edad = 47,
//        username = "mramírez39",
//        contrasenia = "pass4058",
//        telefono = 317690431,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3658.51
//    )
//
//
//    val viajero40 = Viajero(
//        nombre = "Javier",
//        apellido = "Moreno",
//        edad = 64,
//        username = "jmoreno40",
//        contrasenia = "pass8941",
//        telefono = 312250373,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2673.77
//    )
//
//
//    val viajero41 = Viajero(
//        nombre = "Sofia",
//        apellido = "Martínez",
//        edad = 23,
//        username = "smartínez41",
//        contrasenia = "pass9925",
//        telefono = 360953336,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3154.11
//    )
//
//
//    val viajero42 = Viajero(
//        nombre = "Sofia",
//        apellido = "Martínez",
//        edad = 34,
//        username = "smartínez42",
//        contrasenia = "pass5824",
//        telefono = 307749704,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4000.32
//    )
//
//
//    val viajero43 = Viajero(
//        nombre = "Javier",
//        apellido = "Fernández",
//        edad = 61,
//        username = "jfernández43",
//        contrasenia = "pass1120",
//        telefono = 390582782,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3412.97
//    )
//
//
//    val viajero44 = Viajero(
//        nombre = "Carlos",
//        apellido = "Ramírez",
//        edad = 63,
//        username = "cramírez44",
//        contrasenia = "pass5506",
//        telefono = 304118442,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1622.31
//    )
//
//
//    val viajero45 = Viajero(
//        nombre = "Miguel",
//        apellido = "Torres",
//        edad = 41,
//        username = "mtorres45",
//        contrasenia = "pass9887",
//        telefono = 304086836,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1924.5
//    )
//
//
//    val viajero46 = Viajero(
//        nombre = "Elena",
//        apellido = "Martínez",
//        edad = 36,
//        username = "emartínez46",
//        contrasenia = "pass5831",
//        telefono = 348865251,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4951.92
//    )
//
//
//    val viajero47 = Viajero(
//        nombre = "Luis",
//        apellido = "Díaz",
//        edad = 61,
//        username = "ldíaz47",
//        contrasenia = "pass6368",
//        telefono = 301664076,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1561.35
//    )
//
//
//    val viajero48 = Viajero(
//        nombre = "Luis",
//        apellido = "Ruiz",
//        edad = 37,
//        username = "lruiz48",
//        contrasenia = "pass1439",
//        telefono = 386300351,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1167.42
//    )
//
//
//    val viajero49 = Viajero(
//        nombre = "Carlos",
//        apellido = "Torres",
//        edad = 34,
//        username = "ctorres49",
//        contrasenia = "pass8563",
//        telefono = 346523996,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2841.87
//    )
//
//
//    val viajero50 = Viajero(
//        nombre = "Ana",
//        apellido = "Ruiz",
//        edad = 57,
//        username = "aruiz50",
//        contrasenia = "pass3081",
//        telefono = 301611146,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2533.73
//    )
//
//
//    val viajero51 = Viajero(
//        nombre = "Carlos",
//        apellido = "Castro",
//        edad = 62,
//        username = "ccastro51",
//        contrasenia = "pass5900",
//        telefono = 372241928,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4857.23
//    )
//
//
//    val viajero52 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramos",
//        edad = 23,
//        username = "mramos52",
//        contrasenia = "pass5969",
//        telefono = 342219752,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2454.68
//    )
//
//
//    val viajero53 = Viajero(
//        nombre = "Lucia",
//        apellido = "Ramos",
//        edad = 35,
//        username = "lramos53",
//        contrasenia = "pass1904",
//        telefono = 357842951,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4712.4
//    )
//
//
//    val viajero54 = Viajero(
//        nombre = "Elena",
//        apellido = "Martínez",
//        edad = 46,
//        username = "emartínez54",
//        contrasenia = "pass3752",
//        telefono = 358802270,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2705.62
//    )
//
//
//    val viajero55 = Viajero(
//        nombre = "Maria",
//        apellido = "Moreno",
//        edad = 33,
//        username = "mmoreno55",
//        contrasenia = "pass1019",
//        telefono = 369442593,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1443.58
//    )
//
//
//    val viajero56 = Viajero(
//        nombre = "Sofia",
//        apellido = "Moreno",
//        edad = 36,
//        username = "smoreno56",
//        contrasenia = "pass5684",
//        telefono = 334228802,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3473.25
//    )
//
//
//    val viajero57 = Viajero(
//        nombre = "Pedro",
//        apellido = "Ramos",
//        edad = 61,
//        username = "pramos57",
//        contrasenia = "pass2546",
//        telefono = 382792537,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1301.16
//    )
//
//
//    val viajero58 = Viajero(
//        nombre = "Elena",
//        apellido = "Ramírez",
//        edad = 43,
//        username = "eramírez58",
//        contrasenia = "pass3888",
//        telefono = 388368433,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2246.18
//    )
//
//
//    val viajero59 = Viajero(
//        nombre = "Sofia",
//        apellido = "Ruiz",
//        edad = 44,
//        username = "sruiz59",
//        contrasenia = "pass8744",
//        telefono = 379342313,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3232.9
//    )
//
//
//    val viajero60 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramos",
//        edad = 18,
//        username = "mramos60",
//        contrasenia = "pass8261",
//        telefono = 328505501,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2848.28
//    )
//
//
//    val viajero61 = Viajero(
//        nombre = "Lucia",
//        apellido = "Torres",
//        edad = 49,
//        username = "ltorres61",
//        contrasenia = "pass5007",
//        telefono = 327377759,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3563.5
//    )
//
//
//    val viajero62 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramírez",
//        edad = 64,
//        username = "mramírez62",
//        contrasenia = "pass5312",
//        telefono = 349175337,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2697.22
//    )
//
//
//    val viajero63 = Viajero(
//        nombre = "Luis",
//        apellido = "Ruiz",
//        edad = 62,
//        username = "lruiz63",
//        contrasenia = "pass9156",
//        telefono = 344232236,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2360.04
//    )
//
//
//    val viajero64 = Viajero(
//        nombre = "Maria",
//        apellido = "Fernández",
//        edad = 33,
//        username = "mfernández64",
//        contrasenia = "pass9702",
//        telefono = 341713303,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4560.02
//    )
//
//
//    val viajero65 = Viajero(
//        nombre = "Maria",
//        apellido = "Fernández",
//        edad = 31,
//        username = "mfernández65",
//        contrasenia = "pass6645",
//        telefono = 310768852,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4471.14
//    )
//
//
//    val viajero66 = Viajero(
//        nombre = "Javier",
//        apellido = "Torres",
//        edad = 27,
//        username = "jtorres66",
//        contrasenia = "pass8020",
//        telefono = 399994450,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3886.06
//    )
//
//
//    val viajero67 = Viajero(
//        nombre = "Miguel",
//        apellido = "Castro",
//        edad = 55,
//        username = "mcastro67",
//        contrasenia = "pass1188",
//        telefono = 354645384,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2927.33
//    )
//
//
//    val viajero68 = Viajero(
//        nombre = "Javier",
//        apellido = "Ramos",
//        edad = 63,
//        username = "jramos68",
//        contrasenia = "pass8409",
//        telefono = 333092934,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3846.44
//    )
//
//
//    val viajero69 = Viajero(
//        nombre = "Maria",
//        apellido = "Castro",
//        edad = 33,
//        username = "mcastro69",
//        contrasenia = "pass1932",
//        telefono = 373919941,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2103.52
//    )
//
//
//    val viajero70 = Viajero(
//        nombre = "Javier",
//        apellido = "Ramos",
//        edad = 46,
//        username = "jramos70",
//        contrasenia = "pass8846",
//        telefono = 309985935,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2474.77
//    )
//
//
//    val viajero71 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramos",
//        edad = 61,
//        username = "mramos71",
//        contrasenia = "pass5431",
//        telefono = 335999656,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3249.53
//    )
//
//
//    val viajero72 = Viajero(
//        nombre = "Carlos",
//        apellido = "Sánchez",
//        edad = 37,
//        username = "csánchez72",
//        contrasenia = "pass8959",
//        telefono = 398699367,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2386.11
//    )
//
//
//    val viajero73 = Viajero(
//        nombre = "Luis",
//        apellido = "Castro",
//        edad = 48,
//        username = "lcastro73",
//        contrasenia = "pass6437",
//        telefono = 370908567,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3128.54
//    )
//
//
//    val viajero74 = Viajero(
//        nombre = "Pedro",
//        apellido = "Ramos",
//        edad = 48,
//        username = "pramos74",
//        contrasenia = "pass7271",
//        telefono = 349630571,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3090.33
//    )
//
//
//    val viajero75 = Viajero(
//        nombre = "Carlos",
//        apellido = "Torres",
//        edad = 49,
//        username = "ctorres75",
//        contrasenia = "pass8485",
//        telefono = 391748963,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2307.66
//    )
//
//
//    val viajero76 = Viajero(
//        nombre = "Sofia",
//        apellido = "Castro",
//        edad = 50,
//        username = "scastro76",
//        contrasenia = "pass3631",
//        telefono = 308902897,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4847.79
//    )
//
//
//    val viajero77 = Viajero(
//        nombre = "Luis",
//        apellido = "Díaz",
//        edad = 63,
//        username = "ldíaz77",
//        contrasenia = "pass8206",
//        telefono = 349440006,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3795.62
//    )
//
//
//    val viajero78 = Viajero(
//        nombre = "Javier",
//        apellido = "Castro",
//        edad = 39,
//        username = "jcastro78",
//        contrasenia = "pass1627",
//        telefono = 311896784,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4934.21
//    )
//
//
//    val viajero79 = Viajero(
//        nombre = "Javier",
//        apellido = "Moreno",
//        edad = 26,
//        username = "jmoreno79",
//        contrasenia = "pass9492",
//        telefono = 335019520,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3547.21
//    )
//
//
//    val viajero80 = Viajero(
//        nombre = "Luis",
//        apellido = "Díaz",
//        edad = 54,
//        username = "ldíaz80",
//        contrasenia = "pass5938",
//        telefono = 384484053,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3739.92
//    )
//
//
//    val viajero81 = Viajero(
//        nombre = "Ana",
//        apellido = "Ramos",
//        edad = 43,
//        username = "aramos81",
//        contrasenia = "pass9913",
//        telefono = 380249267,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4015.33
//    )
//
//
//    val viajero82 = Viajero(
//        nombre = "Pedro",
//        apellido = "Moreno",
//        edad = 50,
//        username = "pmoreno82",
//        contrasenia = "pass8269",
//        telefono = 311279549,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1703.58
//    )
//
//
//    val viajero83 = Viajero(
//        nombre = "Luis",
//        apellido = "Fernández",
//        edad = 40,
//        username = "lfernández83",
//        contrasenia = "pass8400",
//        telefono = 302547617,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1601.43
//    )
//
//
//    val viajero84 = Viajero(
//        nombre = "Maria",
//        apellido = "Moreno",
//        edad = 42,
//        username = "mmoreno84",
//        contrasenia = "pass4666",
//        telefono = 319059724,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2986.97
//    )
//
//
//    val viajero85 = Viajero(
//        nombre = "Lucia",
//        apellido = "Castro",
//        edad = 37,
//        username = "lcastro85",
//        contrasenia = "pass5978",
//        telefono = 362692746,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4480.58
//    )
//
//
//    val viajero86 = Viajero(
//        nombre = "Pedro",
//        apellido = "Ramos",
//        edad = 25,
//        username = "pramos86",
//        contrasenia = "pass9775",
//        telefono = 352415751,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2052.55
//    )
//
//
//    val viajero87 = Viajero(
//        nombre = "Carlos",
//        apellido = "Castro",
//        edad = 44,
//        username = "ccastro87",
//        contrasenia = "pass4086",
//        telefono = 344533994,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1259.38
//    )
//
//
//    val viajero88 = Viajero(
//        nombre = "Javier",
//        apellido = "Fernández",
//        edad = 57,
//        username = "jfernández88",
//        contrasenia = "pass8744",
//        telefono = 391014232,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2812.9
//    )
//
//
//    val viajero89 = Viajero(
//        nombre = "Maria",
//        apellido = "Moreno",
//        edad = 24,
//        username = "mmoreno89",
//        contrasenia = "pass2930",
//        telefono = 381819413,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3860.62
//    )
//
//
//    val viajero90 = Viajero(
//        nombre = "Miguel",
//        apellido = "Ramírez",
//        edad = 35,
//        username = "mramírez90",
//        contrasenia = "pass3844",
//        telefono = 330633417,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2590.4
//    )
//
//
//    val viajero91 = Viajero(
//        nombre = "Lucia",
//        apellido = "Castro",
//        edad = 39,
//        username = "lcastro91",
//        contrasenia = "pass8461",
//        telefono = 383314871,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3122.01
//    )
//
//
//    val viajero92 = Viajero(
//        nombre = "Carlos",
//        apellido = "Castro",
//        edad = 56,
//        username = "ccastro92",
//        contrasenia = "pass6698",
//        telefono = 301038729,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2317.19
//    )
//
//
//    val viajero93 = Viajero(
//        nombre = "Maria",
//        apellido = "Ramos",
//        edad = 34,
//        username = "mramos93",
//        contrasenia = "pass1578",
//        telefono = 301396874,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4959.23
//    )
//
//
//    val viajero94 = Viajero(
//        nombre = "Miguel",
//        apellido = "Díaz",
//        edad = 50,
//        username = "mdíaz94",
//        contrasenia = "pass8961",
//        telefono = 397626073,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1181.47
//    )
//
//
//    val viajero95 = Viajero(
//        nombre = "Pedro",
//        apellido = "Sánchez",
//        edad = 58,
//        username = "psánchez95",
//        contrasenia = "pass7705",
//        telefono = 321604038,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4145.59
//    )
//
//
//    val viajero96 = Viajero(
//        nombre = "Luis",
//        apellido = "Martínez",
//        edad = 20,
//        username = "lmartínez96",
//        contrasenia = "pass8664",
//        telefono = 319038751,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3213.53
//    )
//
//
//    val viajero97 = Viajero(
//        nombre = "Lucia",
//        apellido = "Ruiz",
//        edad = 36,
//        username = "lruiz97",
//        contrasenia = "pass6303",
//        telefono = 365407488,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 4658.49
//    )
//
//
//    val viajero98 = Viajero(
//        nombre = "Miguel",
//        apellido = "Martínez",
//        edad = 50,
//        username = "mmartínez98",
//        contrasenia = "pass6746",
//        telefono = 382961016,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 1766.91
//    )
//
//
//    val viajero99 = Viajero(
//        nombre = "Lucia",
//        apellido = "Sánchez",
//        edad = 57,
//        username = "lsánchez99",
//        contrasenia = "pass8185",
//        telefono = 396145047,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 3062.75
//    )
//
//
//    val viajero100 = Viajero(
//        nombre = "Elena",
//        apellido = "Fernández",
//        edad = 64,
//        username = "efernández100",
//        contrasenia = "pass6008",
//        telefono = 363995084,
//        esChofer = false,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743014782/dntsqxo0znfbsdvgvygd.png",
//        saldo = 2877.81
//    )


    fun crearUsuarios() {
        val viajeros = listOf(
            viajero1, viajero2, viajero3, viajero4,
//            viajero5, viajero6, viajero7, viajero8, viajero9, viajero10,
//            viajero11, viajero12, viajero13, viajero14, viajero15, viajero16, viajero17, viajero18, viajero19, viajero20,
//            viajero21, viajero22, viajero23, viajero24, viajero25, viajero26, viajero27, viajero28, viajero29, viajero30,
//            viajero31, viajero32, viajero33, viajero34, viajero35, viajero36, viajero37, viajero38, viajero39, viajero40,
//            viajero41, viajero42, viajero43, viajero44, viajero45, viajero46, viajero47, viajero48, viajero49, viajero50,
//            viajero51, viajero52, viajero53, viajero54, viajero55, viajero56, viajero57, viajero58, viajero59, viajero60,
//            viajero61, viajero62, viajero63, viajero64, viajero65, viajero66, viajero67, viajero68, viajero69, viajero70,
//            viajero71, viajero72, viajero73, viajero74, viajero75, viajero76, viajero77, viajero78, viajero79, viajero80,
//            viajero81, viajero82, viajero83, viajero84, viajero85, viajero86, viajero87, viajero88, viajero89, viajero90,
//            viajero91, viajero92, viajero93, viajero94, viajero95, viajero96, viajero97, viajero98, viajero99, viajero100,
//            viajero101, viajero102, viajero103, viajero104
        )
        viajeros.forEach { viajeroRepository.save(it) }
        // Nodes
        val viajerosNode = viajeros.map { ViajeroNode(it) }
        viajerosNode.forEach { viajeroNodeRepository.save(it) }
    }

    // VEHICULOS
    final val vehiculoSimple = Vehiculo(marca = "Toyota", modelo = "Corolla", dominio = "ABC123", anio = 2018)
    final val vehiculoEjecutivo = Vehiculo(marca = "Ford", modelo = "Focus", dominio = "DEF456", anio = 2020)
    final val vehiculoMoto = Vehiculo(marca = "Yamaha", modelo = "FZ25", dominio = "MNO345", anio = 2022)


    // CONDUCTORES
    val conductor1 = Simple(
        nombre = "Luis",
        apellido = "Fernández",
        edad = 35,
        username = "luisf",
        contrasenia = "pass1234",
        telefono = 111222333,
        esChofer = true,
        rol = ROLES.CONDUCTOR,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
        vehiculo = vehiculoSimple,
        precioBaseDelViaje = 400.0
    )
    val conductor2 = Ejecutivo(
        nombre = "Elena",
        apellido = "Ramírez",
        edad = 29,
        username = "elenaR",
        contrasenia = "secure789",
        telefono = 444555666,
        esChofer = true,
        rol = ROLES.CONDUCTOR,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
        vehiculo = vehiculoEjecutivo,
        precioBaseDelViaje = 600.0
    )
    val conductor3 = Moto(
        nombre = "Pedro",
        apellido = "Sánchez",
        edad = 40,
        username = "pedros",
        contrasenia = "mypass567",
        telefono = 777888999,
        esChofer = true,
        rol = ROLES.CONDUCTOR,
        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
        vehiculo = vehiculoMoto,
        precioBaseDelViaje = 300.0
    )
//    val conductor11 = Simple(
//        nombre = "Carlos",
//        apellido = "Gómez",
//        edad = 36,
//        username = "carlosg",
//        contrasenia = "carlos36",
//        telefono = 111111011,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor12 = Ejecutivo(
//        nombre = "Ana",
//        apellido = "López",
//        edad = 31,
//        username = "anal",
//        contrasenia = "ana31",
//        telefono = 111111012,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor13 = Moto(
//        nombre = "Javier",
//        apellido = "Martínez",
//        edad = 42,
//        username = "javierm",
//        contrasenia = "javier42",
//        telefono = 111111013,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor14 = Simple(
//        nombre = "Lucía",
//        apellido = "Ruiz",
//        edad = 34,
//        username = "luciar",
//        contrasenia = "lucia34",
//        telefono = 111111014,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor15 = Ejecutivo(
//        nombre = "Miguel",
//        apellido = "Díaz",
//        edad = 45,
//        username = "migueld",
//        contrasenia = "miguel45",
//        telefono = 111111015,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor16 = Moto(
//        nombre = "Sofía",
//        apellido = "Moreno",
//        edad = 28,
//        username = "sofiam",
//        contrasenia = "sofia28",
//        telefono = 111111016,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor17 = Simple(
//        nombre = "Diego",
//        apellido = "Ramos",
//        edad = 37,
//        username = "diegor",
//        contrasenia = "diego37",
//        telefono = 111111017,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor18 = Ejecutivo(
//        nombre = "Valentina",
//        apellido = "Torres",
//        edad = 30,
//        username = "valentinat",
//        contrasenia = "valentina30",
//        telefono = 111111018,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor19 = Moto(
//        nombre = "Andrés",
//        apellido = "Castro",
//        edad = 39,
//        username = "andresc",
//        contrasenia = "andres39",
//        telefono = 111111019,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor20 = Simple(
//        nombre = "Paula",
//        apellido = "Delgado",
//        edad = 33,
//        username = "paulad",
//        contrasenia = "paula33",
//        telefono = 111111020,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor21 = Ejecutivo(
//        nombre = "Fernando",
//        apellido = "Herrera",
//        edad = 41,
//        username = "fernandoh",
//        contrasenia = "fernando41",
//        telefono = 111111021,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor22 = Moto(
//        nombre = "Natalia",
//        apellido = "Reyes",
//        edad = 27,
//        username = "nataliar",
//        contrasenia = "natalia27",
//        telefono = 111111022,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor23 = Simple(
//        nombre = "Jorge",
//        apellido = "Navarro",
//        edad = 38,
//        username = "jorgen",
//        contrasenia = "jorge38",
//        telefono = 111111023,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor24 = Ejecutivo(
//        nombre = "Camila",
//        apellido = "Ortiz",
//        edad = 32,
//        username = "camilao",
//        contrasenia = "camila32",
//        telefono = 111111024,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor25 = Moto(
//        nombre = "Mario",
//        apellido = "Vargas",
//        edad = 35,
//        username = "mariov",
//        contrasenia = "mario35",
//        telefono = 111111025,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor26 = Simple(
//        nombre = "Isabel",
//        apellido = "Fuentes",
//        edad = 29,
//        username = "isabelf",
//        contrasenia = "isabel29",
//        telefono = 111111026,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor27 = Ejecutivo(
//        nombre = "Oscar",
//        apellido = "Silva",
//        edad = 44,
//        username = "oscars",
//        contrasenia = "oscar44",
//        telefono = 111111027,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor28 = Moto(
//        nombre = "Daniela",
//        apellido = "Cruz",
//        edad = 26,
//        username = "danielac",
//        contrasenia = "daniela26",
//        telefono = 111111028,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor29 = Simple(
//        nombre = "Alberto",
//        apellido = "Mora",
//        edad = 40,
//        username = "albertom",
//        contrasenia = "alberto40",
//        telefono = 111111029,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor30 = Ejecutivo(
//        nombre = "Laura",
//        apellido = "Pérez",
//        edad = 36,
//        username = "laurap",
//        contrasenia = "laura36",
//        telefono = 111111030,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor31 = Moto(
//        nombre = "Renata",
//        apellido = "Zamora",
//        edad = 30,
//        username = "renataz",
//        contrasenia = "renata30",
//        telefono = 111111031,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor32 = Simple(
//        nombre = "Gabriel",
//        apellido = "Salas",
//        edad = 33,
//        username = "gabriels",
//        contrasenia = "gabriel33",
//        telefono = 111111032,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor33 = Ejecutivo(
//        nombre = "Mariana",
//        apellido = "Esquivel",
//        edad = 38,
//        username = "marianae",
//        contrasenia = "mariana38",
//        telefono = 111111033,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor34 = Moto(
//        nombre = "Rodrigo",
//        apellido = "Álvarez",
//        edad = 29,
//        username = "rodrigoa",
//        contrasenia = "rodrigo29",
//        telefono = 111111034,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor35 = Simple(
//        nombre = "Claudia",
//        apellido = "Méndez",
//        edad = 39,
//        username = "claudiam",
//        contrasenia = "claudia39",
//        telefono = 111111035,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor36 = Ejecutivo(
//        nombre = "Sebastián",
//        apellido = "Iglesias",
//        edad = 37,
//        username = "sebastiani",
//        contrasenia = "sebastian37",
//        telefono = 111111036,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor37 = Moto(
//        nombre = "Verónica",
//        apellido = "Núñez",
//        edad = 34,
//        username = "veronican",
//        contrasenia = "veronica34",
//        telefono = 111111037,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor38 = Simple(
//        nombre = "Héctor",
//        apellido = "Campos",
//        edad = 41,
//        username = "hectorc",
//        contrasenia = "hector41",
//        telefono = 111111038,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor39 = Ejecutivo(
//        nombre = "Patricia",
//        apellido = "Gutiérrez",
//        edad = 43,
//        username = "patriciag",
//        contrasenia = "patricia43",
//        telefono = 111111039,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor40 = Moto(
//        nombre = "Iván",
//        apellido = "Luna",
//        edad = 36,
//        username = "ivanl",
//        contrasenia = "ivan36",
//        telefono = 111111040,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor41 = Simple(
//        nombre = "Liliana",
//        apellido = "Acosta",
//        edad = 32,
//        username = "lilianaa",
//        contrasenia = "liliana32",
//        telefono = 111111041,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor42 = Ejecutivo(
//        nombre = "Tomás",
//        apellido = "Del Río",
//        edad = 30,
//        username = "tomasd",
//        contrasenia = "tomas30",
//        telefono = 111111042,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor43 = Moto(
//        nombre = "Noelia",
//        apellido = "Ríos",
//        edad = 27,
//        username = "noeliar",
//        contrasenia = "noelia27",
//        telefono = 111111043,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor44 = Simple(
//        nombre = "Ángel",
//        apellido = "Serrano",
//        edad = 44,
//        username = "angels",
//        contrasenia = "angel44",
//        telefono = 111111044,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor45 = Ejecutivo(
//        nombre = "Teresa",
//        apellido = "Muñoz",
//        edad = 35,
//        username = "teresam",
//        contrasenia = "teresa35",
//        telefono = 111111045,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor46 = Moto(
//        nombre = "Esteban",
//        apellido = "López",
//        edad = 33,
//        username = "estebanl",
//        contrasenia = "esteban33",
//        telefono = 111111046,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor47 = Simple(
//        nombre = "Lorena",
//        apellido = "Bravo",
//        edad = 31,
//        username = "lorenab",
//        contrasenia = "lorena31",
//        telefono = 111111047,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )
//
//    val conductor48 = Ejecutivo(
//        nombre = "Bruno",
//        apellido = "Sosa",
//        edad = 39,
//        username = "brunos",
//        contrasenia = "bruno39",
//        telefono = 111111048,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109542/s4fwodoxz5tk9m9zhabc.jpg",
//        vehiculo = vehiculoEjecutivo,
//        precioBaseDelViaje = 600.0
//    )
//
//    val conductor49 = Moto(
//        nombre = "Rocío",
//        apellido = "Peña",
//        edad = 28,
//        username = "rociop",
//        contrasenia = "rocio28",
//        telefono = 111111049,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1743109341/jg5csiav1bee9wlugrfj.jpg",
//        vehiculo = vehiculoMoto,
//        precioBaseDelViaje = 300.0
//    )
//
//    val conductor50 = Simple(
//        nombre = "Emilio",
//        apellido = "Rueda",
//        edad = 42,
//        username = "emilior",
//        contrasenia = "emilio42",
//        telefono = 111111050,
//        esChofer = true,
//        rol = ROLES.CONDUCTOR,
//        foto = "https://res.cloudinary.com/diezou2of/image/upload/v1748375662/bcsidweeub4skeci8seq.jpg",
//        vehiculo = vehiculoSimple,
//        precioBaseDelViaje = 400.0
//    )




    fun crearChoferes() {
//        listOf(conductor1, conductor2, conductor3).forEach { conductorRepository.save(it) }
//        val conductoresNode = listOf(conductor1, conductor2, conductor3).map { ConductorNode(it) }
//        conductoresNode.map { conductorNodeRepository.save(it) }


        val conductores = listOf(
            conductor1, conductor2, conductor3,
//            conductor11, conductor12, conductor13, conductor14, conductor15,
//            conductor16, conductor17, conductor18, conductor19, conductor20, conductor21, conductor22,
//            conductor23, conductor24, conductor25, conductor26, conductor27, conductor28, conductor29,
//            conductor30, conductor31, conductor32, conductor33, conductor34, conductor35, conductor36,
//            conductor37, conductor38, conductor39, conductor40, conductor41, conductor42, conductor43,
//            conductor44, conductor45, conductor46, conductor47, conductor48, conductor49, conductor50
        )

        conductores.forEach { conductorRepository.save(it) }
        // Nodes
        val conductoresNode = conductores.map { ConductorNode(it) }
        conductoresNode.forEach { conductorNodeRepository.save(it) }
    }

    // VIAJES
    fun crearViajes() {
        val viajes = mutableListOf<Viaje>()
        val conductores = listOf(conductor1, conductor2, conductor3)
        val viajeros = listOf(viajero1, viajero2, viajero3, viajero4)

        for (conductor in conductores) {
            repeat(8) {
                val viajero = viajeros.random()
                val fechaInicio = if (it % 2 == 0) LocalDateTime.now().minusDays(it.toLong()) else LocalDateTime.now()
                    .plusDays(it.toLong())
                val duracion = (5..20).random()

                viajes.add(
                    Viaje(
                        viajero = viajero,
                        conductorId = conductor.id,
                        origen = "Ciudad ${it + 1}",
                        destino = "Destino ${it + 1}",
                        fechaInicio = fechaInicio,
                        fechaFin = fechaInicio.plusMinutes(duracion.toLong()),
                        cantidadDePasajeros = (1..3).random(),
                        duracion = duracion,
                        importe = conductor.importeViaje((1..3).random(), (5..20).random()),
                        nombreYApellidoConductor = conductor.nombreYApellido(),
                        fotoConductor = conductor.foto

                    )
                )
            }
        }
//       viajes.forEach { viajeRepository.save(it)}
//       viajes.forEach { dataViajeRepository.save(DataViaje(it.id,it.conductorId,it.fechaInicio, it.fechaFin))}
        viajes.forEach { viaje ->
            val viajeGuardado = viajeRepository.save(viaje)
            dataViajeRepository.save(
                DataViaje(
                    id = viajeGuardado.id,
                    conductorId = viajeGuardado.conductorId,
                    fechaInicio = viajeGuardado.fechaInicio,
                    fechaFin = viajeGuardado.fechaFin
                )
            )
        }


    }

    // AMISTADES
    fun crearAmistades() {
        viajeroNodeRepository.crearAmistad(viajero1.id, viajero2.id)
//        viajeroNodeRepository.crearAmistad(viajero1.id, viajero3.id)
//        viajeroNodeRepository.crearAmistad(viajero1.id, viajero4.id)
    }

    // COMENTARIOS
//    fun crearComentarios() {
//        val viajesRealizados = viajeRepository.findAll().filter { it.fechaInicio.isBefore(LocalDateTime.now()) }
//        val comentarios = mutableListOf<Comentario>()
//
//        viajesRealizados.take(5).forEach {
//            comentarios.add(
//                Comentario(
//                    viaje = it,
//                    estrellas = (3..5).random(),
//                    mensaje = "Comentario sobre el viaje de ${it.viajero.nombre} con ${it.conductor.nombre}.",
//                    fecha = LocalDate.now()
//                )
//            )
//        }
//        comentarios.forEach { usuarioService.calificarViaje(it.viaje.viajero.id, CalificacionDTO(it.viaje.id, it.estrellas, it.mensaje)) }
//    }
}

