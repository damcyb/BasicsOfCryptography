import image.ImageHandler

fun main() {

    val imageHandler = ImageHandler("./src/main/resources/panda.png")
    val encoded = imageHandler.encode("Testowa wiadomosc nie moze byc za dluga, poniewaz moze zabraknac pikseli do jej zakodowania.")
    imageHandler.writeImage(encoded, "./src/main/resources/pandaEncoded.png")
    val output = imageHandler.decode(encoded)
    println(output)

}