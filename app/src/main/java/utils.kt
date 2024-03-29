object utils {

    public fun parseImageUrls(imagesString: String): List<String>{
        return imagesString.split(';')
    }
}