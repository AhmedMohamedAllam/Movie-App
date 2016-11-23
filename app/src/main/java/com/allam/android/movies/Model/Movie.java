package com.allam.android.movies.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Allam on 07/08/2016.
 */
public class Movie {

    /**
     * page : 1
     * results : [{"poster_path":"/5N20rQURev5CNDcMjHVUZhpoCNC.jpg","adult":false,"overview":"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.","release_date":"2016-04-27","genre_ids":[28,53,878],"id":271110,"original_title":"Captain America: Civil War","original_language":"en","title":"Captain America: Civil War","backdrop_path":"/rqAHkvXldb9tHlnbQDwOzRi0yVD.jpg","popularity":57.042383,"vote_count":2693,"video":false,"vote_average":6.93},{"poster_path":"/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg","adult":false,"overview":"From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.","release_date":"2016-08-03","genre_ids":[14,28,80],"id":297761,"original_title":"Suicide Squad","original_language":"en","title":"Suicide Squad","backdrop_path":"/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg","popularity":42.740013,"vote_count":1668,"video":false,"vote_average":5.91},{"poster_path":"/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg","adult":false,"overview":"The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.","release_date":"2016-07-27","genre_ids":[28,53],"id":324668,"original_title":"Jason Bourne","original_language":"en","title":"Jason Bourne","backdrop_path":"/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg","popularity":33.65775,"vote_count":744,"video":false,"vote_average":5.32},{"poster_path":"/h28t2JNNGrZx0fIuAw8aHQFhIxR.jpg","adult":false,"overview":"A recently cheated on married woman falls for a younger man who has moved in next door, but their torrid affair soon takes a dangerous turn.","release_date":"2015-01-23","genre_ids":[53],"id":241251,"original_title":"The Boy Next Door","original_language":"en","title":"The Boy Next Door","backdrop_path":"/vj4IhmH4HCMZYYjTMiYBybTWR5o.jpg","popularity":21.807944,"vote_count":666,"video":false,"vote_average":4.09},{"poster_path":"/hU0E130tsGdsYa4K9lc3Xrn5Wyt.jpg","adult":false,"overview":"One year after outwitting the FBI and winning the public\u2019s adulation with their mind-bending spectacles, the Four Horsemen resurface only to find themselves face to face with a new enemy who enlists them to pull off their most dangerous heist yet.","release_date":"2016-06-02","genre_ids":[28,12,35,80,9648,53],"id":291805,"original_title":"Now You See Me 2","original_language":"en","title":"Now You See Me 2","backdrop_path":"/zrAO2OOa6s6dQMQ7zsUbDyIBrAP.jpg","popularity":20.944694,"vote_count":771,"video":false,"vote_average":6.66},{"poster_path":"/vOipe2myi26UDwP978hsYOrnUWC.jpg","adult":false,"overview":"After a threat from the tiger Shere Khan forces him to flee the jungle, a man-cub named Mowgli embarks on a journey of self discovery with the help of panther, Bagheera, and free spirited bear, Baloo.","release_date":"2016-04-07","genre_ids":[12,18,14],"id":278927,"original_title":"The Jungle Book","original_language":"en","title":"The Jungle Book","backdrop_path":"/eIOTsGg9FCVrBc4r2nXaV61JF4F.jpg","popularity":20.803907,"vote_count":1159,"video":false,"vote_average":6.37},{"poster_path":"/tgfRDJs5PFW20Aoh1orEzuxW8cN.jpg","adult":false,"overview":"Arthur Bishop thought he had put his murderous past behind him when his most formidable foe kidnaps the love of his life. Now he is forced to travel the globe to complete three impossible assassinations, and do what he does best, make them look like accidents.","release_date":"2016-08-25","genre_ids":[80,28,53],"id":278924,"original_title":"Mechanic: Resurrection","original_language":"en","title":"Mechanic: Resurrection","backdrop_path":"/3oRHlbxMLBXHfMqUsx1emwqiuQ3.jpg","popularity":20.050811,"vote_count":193,"video":false,"vote_average":4.5},{"poster_path":"/cGOPbv9wA5gEejkUN892JrveARt.jpg","adult":false,"overview":"Fearing the actions of a god-like Super Hero left unchecked, Gotham City\u2019s own formidable, forceful vigilante takes on Metropolis\u2019s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it\u2019s ever known before.","release_date":"2016-03-23","genre_ids":[28,12,14],"id":209112,"original_title":"Batman v Superman: Dawn of Justice","original_language":"en","title":"Batman v Superman: Dawn of Justice","backdrop_path":"/vsjBeMPZtyB7yNsYY56XYxifaQZ.jpg","popularity":17.497923,"vote_count":3547,"video":false,"vote_average":5.52},{"poster_path":"/kqjL17yufvn9OVLyXYpvtyrFfak.jpg","adult":false,"overview":"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.","release_date":"2015-05-13","genre_ids":[28,12,878,53],"id":76341,"original_title":"Mad Max: Fury Road","original_language":"en","title":"Mad Max: Fury Road","backdrop_path":"/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg","popularity":17.338471,"vote_count":5274,"video":false,"vote_average":7.25},{"poster_path":"/gj282Pniaa78ZJfbaixyLXnXEDI.jpg","adult":false,"overview":"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.","release_date":"2014-11-18","genre_ids":[878,12,53],"id":131631,"original_title":"The Hunger Games: Mockingjay - Part 1","original_language":"en","title":"The Hunger Games: Mockingjay - Part 1","backdrop_path":"/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg","popularity":15.232602,"vote_count":3199,"video":false,"vote_average":6.69},{"poster_path":"/e3lBJCedHnZPfNfmBArKHZXXNC0.jpg","adult":false,"overview":"Lorraine and Ed Warren travel to north London to help a single mother raising four children alone in a house plagued by malicious spirits.","release_date":"2016-06-08","genre_ids":[27],"id":259693,"original_title":"The Conjuring 2","original_language":"en","title":"The Conjuring 2","backdrop_path":"/w9ENTWwpVJYoUky0gg8szmiJuyx.jpg","popularity":14.898759,"vote_count":617,"video":false,"vote_average":6.48},{"poster_path":"/jrBTbqBojXCEPdmYH0ALBWXFqiW.jpg","adult":false,"overview":"The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.","release_date":"2016-06-24","genre_ids":[12,16,35,10751],"id":328111,"original_title":"The Secret Life of Pets","original_language":"en","title":"The Secret Life of Pets","backdrop_path":"/3DrUqTAPjriEasoGrz5G8sPJtDU.jpg","popularity":14.778762,"vote_count":653,"video":false,"vote_average":5.7},{"poster_path":"/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg","adult":false,"overview":"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.","release_date":"2014-11-05","genre_ids":[12,18,878],"id":157336,"original_title":"Interstellar","original_language":"en","title":"Interstellar","backdrop_path":"/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg","popularity":14.553011,"vote_count":5633,"video":false,"vote_average":8.11},{"poster_path":"/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","adult":false,"overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.","release_date":"2015-06-09","genre_ids":[28,12,878,53],"id":135397,"original_title":"Jurassic World","original_language":"en","title":"Jurassic World","backdrop_path":"/dkMD5qlogeRMiEixC4YNPUvax2T.jpg","popularity":14.076079,"vote_count":4961,"video":false,"vote_average":6.59},{"poster_path":"/inVq3FRqcYIRl2la8iZikYYxFNR.jpg","adult":false,"overview":"Based upon Marvel Comics\u2019 most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.","release_date":"2016-02-09","genre_ids":[28,12,35,10749],"id":293660,"original_title":"Deadpool","original_language":"en","title":"Deadpool","backdrop_path":"/nbIrDhOtUpdD9HKDBRy02a8VhpV.jpg","popularity":14.052729,"vote_count":4900,"video":false,"vote_average":7.16},{"poster_path":"/5TQ6YDmymBpnF005OyoB7ohZps9.jpg","adult":false,"overview":"After the cataclysmic events in New York with The Avengers, Steve Rogers, aka Captain America is living quietly in Washington, D.C. and trying to adjust to the modern world. But when a S.H.I.E.L.D. colleague comes under attack, Steve becomes embroiled in a web of intrigue that threatens to put the world at risk. Joining forces with the Black Widow, Captain America struggles to expose the ever-widening conspiracy while fighting off professional assassins sent to silence him at every turn. When the full scope of the villainous plot is revealed, Captain America and the Black Widow enlist the help of a new ally, the Falcon. However, they soon find themselves up against an unexpected and formidable enemy\u2014the Winter Soldier.","release_date":"2014-03-20","genre_ids":[28,12,878],"id":100402,"original_title":"Captain America: The Winter Soldier","original_language":"en","title":"Captain America: The Winter Soldier","backdrop_path":"/4qfXT9BtxeFuamR4F49m2mpKQI1.jpg","popularity":12.70364,"vote_count":3511,"video":false,"vote_average":7.59},{"poster_path":"/oN5lELHH5Xheiy0YdhnY3JB4hx2.jpg","adult":false,"overview":"A small town girl is caught between dead-end jobs. A high-profile, successful man becomes wheelchair bound following an accident. The man decides his life is not worth living until the girl is hired for six months to be his new caretaker. Worlds apart and trapped together by circumstance, the two get off to a rocky start. But the girl becomes determined to prove to the man that life is worth living and as they embark on a series of adventures together, each finds their world changing in ways neither of them could begin to imagine.","release_date":"2016-03-03","genre_ids":[18,10749],"id":296096,"original_title":"Me Before You","original_language":"en","title":"Me Before You","backdrop_path":"/o4lxNwKJz8oq3R0kLOIsDlHbDhZ.jpg","popularity":11.985289,"vote_count":571,"video":false,"vote_average":7.41},{"poster_path":"/q7GOSxRsBLTJysmAEjFoQZfScsq.jpg","adult":false,"overview":"After he reunites with an old pal through Facebook, a mild-mannered accountant is lured into the world of international espionage.","release_date":"2016-06-15","genre_ids":[28,35],"id":302699,"original_title":"Central Intelligence","original_language":"en","title":"Central Intelligence","backdrop_path":"/nX64YARJo4OugPTSVuvFwZzcqM4.jpg","popularity":11.982808,"vote_count":458,"video":false,"vote_average":5.95},{"poster_path":"/il9XWx5CbNd2KdDUwrcClEZiLkv.jpg","adult":false,"overview":"Last months of World War II in April 1945. As the Allies make their final push in the European Theater, a battle-hardened U.S. Army sergeant in the 2nd Armored Division named Wardaddy commands a Sherman tank called \"Fury\" and its five-man crew on a deadly mission behind enemy lines. Outnumbered and outgunned, Wardaddy and his men face overwhelming odds in their heroic attempts to strike at the heart of Nazi Germany.","release_date":"2014-10-15","genre_ids":[10752,18,28],"id":228150,"original_title":"Fury","original_language":"en","title":"Fury","backdrop_path":"/pKawqrtCBMmxarft7o1LbEynys7.jpg","popularity":11.637242,"vote_count":2350,"video":false,"vote_average":7.43},{"poster_path":"/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg","adult":false,"overview":"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.","release_date":"2014-07-30","genre_ids":[28,878,12],"id":118340,"original_title":"Guardians of the Galaxy","original_language":"en","title":"Guardians of the Galaxy","backdrop_path":"/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg","popularity":11.633517,"vote_count":5031,"video":false,"vote_average":7.96}]
     * total_results : 19641
     * total_pages : 983
     */

    private int total_pages;
    /**
     * poster_path : /5N20rQURev5CNDcMjHVUZhpoCNC.jpg
     * adult : false
     * overview : Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.
     * release_date : 2016-04-27
     * genre_ids : [28,53,878]
     * id : 271110
     * original_title : Captain America: Civil War
     * original_language : en
     * title : Captain America: Civil War
     * backdrop_path : /rqAHkvXldb9tHlnbQDwOzRi0yVD.jpg
     * popularity : 57.042383
     * vote_count : 2693
     * video : false
     * vote_average : 6.93
     */

    private List<ResultsBean> results;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {
        private String poster_path;
        private String overview;
        private String release_date;
        private int id;
        private String original_title;
        private double vote_average;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }
    }
}