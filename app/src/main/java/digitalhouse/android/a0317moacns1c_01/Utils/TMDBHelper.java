package digitalhouse.android.a0317moacns1c_01.Utils;

/**
 * Created by Danilo on 08/06/2017.
 */

public class TMDBHelper {

    private static String apiKey = "0937113d999e46fcd683f3dece104000";

    public static String youTubeApiKey = "AIzaSyDtpu9H4TtCNEBKzuSvSlFsMRiRnf_0jGQ";

    private static String baseUrl = "https://api.themoviedb.org/3";

    public static final String TIPO_SERIE = "serie";
    public static final String TIPO_PELICULA = "pelicula";

    public static final String language_ENGLISH = "en-US";
    public static final String language_SPANISH = "es-ES";

    public static final String IMAGE_SIZE_W45 = "w45";
    public static final String IMAGE_SIZE_W92= "w92";
    public static final String IMAGE_SIZE_W154 = "w154";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W300 = "w300";
    public static final String IMAGE_SIZE_W342 = "w342";
    public static final String IMAGE_SIZE_W780 = "w780";
    public static final String IMAGE_SIZE_W1280 = "w1280";
    public static final String IMAGE_SIZE_ORIGINAL = "original";
    public static final String IMAGE_SIZE_H632 = "h632";

    public static final String MOVIE_GENRE_ACTION = "28";
    public static final String MOVIE_GENRE_ADVENTURE = "12";
    public static final String MOVIE_GENRE_ANIMATION = "16";
    public static final String MOVIE_GENRE_COMEDY = "35";
    public static final String MOVIE_GENRE_CRIME = "80";
    public static final String MOVIE_GENRE_DOCUMENTARY = "99";
    public static final String MOVIE_GENRE_DRAMA = "18";
    public static final String MOVIE_GENRE_FAMILY = "10751";
    public static final String MOVIE_GENRE_FANTASY = "14";
    public static final String MOVIE_GENRE_HISTORY = "36";
    public static final String MOVIE_GENRE_HORROR = "27";
    public static final String MOVIE_GENRE_MUSIC = "10402";
    public static final String MOVIE_GENRE_MYSTERY = "9648";
    public static final String MOVIE_GENRE_ROMANCE = "10749";
    public static final String MOVIE_GENRE_SCIENCE_FICTION = "878";
    public static final String MOVIE_GENRE_SCIENCE_TV_MOVIE = "10770";
    public static final String MOVIE_GENRE_SCIENCE_THRILLER = "53";
    public static final String MOVIE_GENRE_SCIENCE_WAR = "10752";
    public static final String MOVIE_GENRE_SCIENCE_WESTERN = "37";

    public static final String TV_GENRE_ACTION_AND_ADVENTURE = "10759";
    public static final String TV_GENRE_ANIMATION = "16";
    public static final String TV_GENRE_COMEDY = "35";
    public static final String TV_GENRE_CRIME = "80";
    public static final String TV_GENRE_DOCUMENTARY = "99";
    public static final String TV_GENRE_DRAMA = "18";
    public static final String TV_GENRE_FAMILY = "10751";
    public static final String TV_GENRE_KIDS = "10762";
    public static final String TV_GENRE_MYSTERY = "9648";
    public static final String TV_GENRE_NEWS = "10763";
    public static final String TV_GENRE_REALITY = "10764";
    public static final String TV_GENRE_SCI_FI_AND_FANTASY = "10765";


    public static final String TV_GENRE_FANTASY = "14";
    public static final String TV_GENRE_HISTORY = "36";
    public static final String TV_GENRE_HORROR = "27";
    public static final String TV_GENRE_MUSIC = "10402";
    public static final String TV_GENRE_ROMANCE = "10749";


    public static String getMovieDetailURL(String movieID,String language){
        return baseUrl + "/movie/" + movieID + "?api_key=" + apiKey +"&language="+language;
    }

    // https://api.themoviedb.org/3/movie/297762/videos?api_key=0937113d999e46fcd683f3dece104000&language=en-US
    public static String getTrailerURL(String movideID,String language){
        return baseUrl + "/movie/" + movideID + "/videos?api_key=" +apiKey +"&language="+language;
    }

    public static String getMoviesRecomended(String movideID, String language, Integer page){
        return baseUrl + "/movie/" + movideID + "/recomendations?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getSimilarMovies(String movideID,String language, Integer page){
        return baseUrl + "/movie/" + movideID + "/similar?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getMoviesReviews(String movideID,String language, Integer page){
        return baseUrl + "/movie/" + movideID + "/reviews?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getMoviePlayList(String movideID,String language, Integer page){
        return baseUrl + "/movie/" + movideID + "/lists?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getLastestMovie(String language, Integer page){
        return baseUrl + "/movie/"+"latest?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getNowPlayingMovies(String language, Integer page){
        return baseUrl + "/movie/" + "now_playing?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getPopularMovies(String language, Integer page){
        return baseUrl + "/movie/" + "popular?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getBestMoviesOfSpecificYear(String language, String specificYear, Integer page){

        return baseUrl + "/discover/movie" + "?api_key=" + apiKey + "&primary_release_year=" + specificYear +
                "&sort_by=vote_average.desc&language=" + language + "&page=" + page.toString();
    }

    public static String getHighestGrossingMovies(String language_ENGLISH,Integer page,String specificYear){

        return baseUrl + "/discover/movie?api_key=" + apiKey + "&sort_by=revenue.desc" + "&primary_release_year=" + specificYear +
                "&language=" + language_ENGLISH + "&page=" + page.toString();
    }

    public static String getTopRatedMovies(String language, Integer page){
        return baseUrl + "/discover/movie/" + "?api_key=" +apiKey +"&sort_by=vote_average.desc&language="+language+"&page="+page.toString();
    }

    public static String getUpcomingMovies(String language, Integer page){
        return baseUrl + "/movie/" + "upcoming?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getImagePoster(String size, String imagePath){
        return "https://image.tmdb.org/t/p/"+size+imagePath;
    }

    // DEVUELVE UNA LISTA DE GENEROS
    // https://api.themoviedb.org/3/genre/list?api_key=0937113d999e46fcd683f3dece104000&language=es-ES
    public static String getAllGenres(String language){
        return baseUrl + "/genre/list?api_key=" +apiKey +"&language="+language;
    }

    public static String getNombreGenero(String generoID){

        String nombreGenero = null;

        switch (generoID){

            case ("28"):
                nombreGenero = "Acción";
                break;
            case ("12"):
                nombreGenero = "Aventura";
                break;
            case ("16"):
                nombreGenero = "Animación";
                break;
            case ("35"):
                nombreGenero = "Comedia";
                break;
            case ("80"):
                nombreGenero = "Crimen";
                break;
            case ("99"):
                nombreGenero = "Documental";
                break;
            case ("18"):
                nombreGenero = "Drama";
                break;
            case ("10751"):
                nombreGenero = "Familia";
                break;
            case ("14"):
                nombreGenero = "Fantasía";
                break;
            case ("36"):
                nombreGenero = "Historia";
                break;
            case ("27"):
                nombreGenero = "Terror";
                break;
            case ("10402"):
                nombreGenero = "Música";
                break;
            case ("9648"):
                nombreGenero = "Misterio";
                break;
            case ("878"):
                nombreGenero = "Ciencia ficción";
                break;
            case ("10770"):
                nombreGenero = "Película de la televisión";
                break;
            case ("53"):
                nombreGenero = "Suspenso";
                break;
            case ("10752"):
                nombreGenero = "Guerra";
                break;
            case ("37"):
                nombreGenero = "Western";
                break;
            }

            return nombreGenero;
        }

    // DEVUELVE UNA LISTA DE PELICULAS DEL GENERO SELECCIONADO (DEBE PASARSE EL CODIGO DE GENERO)
    //https://api.themoviedb.org/3/discover/movie?api_key=0937113d999e46fcd683f3dece104000&language=es-ES&page=1&sort_by=popularity.desc&include_adult=false&include_video=true&page=1&with_genres=28
    public static String getMoviesByGenre(String genre, Integer page, String language){
        return baseUrl + "/discover/movie?api_key=" +apiKey +"&language="+language+"&sort_by=popularity.desc&include_adult=false&include_video=true"+"&page="+page.toString()+"&with_genres="+genre;
    }

    // DEVUELVE UNA LISTA DE SERIES DEL GENERO SELECCIONADO (DEBE PASARSE EL CODIGO DE GENERO)
    //https://api.themoviedb.org/3/discover/tv?api_key=0937113d999e46fcd683f3dece104000&language=es-ES&page=1&sort_by=popularity.desc&include_adult=false&include_video=true&page=1&with_genres=28&include_null_first_air_dates=false
    public static String getTVByGenre(String genre, Integer page,String language){
        return baseUrl + "/discover/tv?api_key=" +apiKey +"&language="+language+"&sort_by=popularity.desc&include_adult=false&include_video=true"+"&page="+page.toString()+"&with_genres="+genre+"&include_null_first_air_dates=false";
    }

    public static String getTVShowDetail(String tvShow,String language){
        return baseUrl + "/tv/"+tvShow+"?api_key=" +apiKey +"&language="+language;
    }

    public static String getTVShowRecomendedForTVShow(String tvShow,String language, Integer page){
        return baseUrl + "/tv/"+tvShow+"/recomendations?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getTVPopular(String language, Integer page){
        return baseUrl + "/tv/popular?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getTVTopRated(String language, Integer page){
        return baseUrl + "/tv/on_the_air?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
    }

    public static String getTVShowVideo(String language, String tvShowId){
        return baseUrl + "/tv/"+tvShowId+"/videos?api_key=" +apiKey +"&language="+language;
    }

    public static String getTVSeasonDetail(String tvShowId, Integer season, String language){
        return baseUrl + "/tv/"+tvShowId+"/season/"+season.toString()+"?api_key=" +apiKey +"&language="+language;
    }

    public static String getTVEpisodeDetail(String tvShowId, Integer season, String language){
        return baseUrl + "/tv/"+tvShowId+"/season/"+season.toString()+"?api_key=" +apiKey +"&language="+language;
    }
    public static String getTVAiringToday(String language, Integer page){
        return baseUrl + "/tv/airing_today?api_key=" + apiKey + "&language="+language + "&page=" +page.toString();

    }

    //api.themoviedb.org/3/search/multi?api_key=<<api_key>>&language=en-US&include_adult=false
    public static String getMultiSearch(String language,String query){

        return baseUrl + "/search/multi?api_key=" + apiKey + "&language=" + language + "&query=" + query + "&include_adult=false";

    }


}