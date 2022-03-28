package com.sabi.globaladmin.runner;



import com.sabi.globaladmin.model.*;
import com.sabi.globaladmin.repository.*;
import com.sabi.globaladmin.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Component
public class DataSeed implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private LGARepository localGovernmentRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppCodesRepository appCodesRepository;





    private Logger log = LogManager.getLogger(DataSeed.class);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        seedCountries();
        seedStates();
        seedLocalGovernments();
        seedBanks();
        seedIntegrationUsers();
        seedAdminUser();
        seedAppCode();

    }


    private void seedCountries() {
        List<Country> countries = new ArrayList<Country>() {
            {
                add(new Country("Nigeria","234"));
                add(new Country("Ghana", "233"));
                add(new Country("Kenya", "254"));
                add(new Country("Uganda", "256"));
                 add(new Country("Angola", "244"));
                 add(new Country("Cameroon", "237"));
                 add(new Country("Central African Republic", "236"));
                 add(new Country("Chad", "235"));
                 add(new Country("D.R Congo", "243"));
                 add(new Country("Republic of the Congo", "242"));
                 add(new Country("Equatorial Guinea", "240"));
                 add(new Country("Gabon", "241"));
                 add(new Country("São Tomé and Príncipe", "239"));
                 add(new Country("Burundi", "257"));
                 add(new Country("Comoros", "269"));
                 add(new Country("Madagascar", "261"));
                 add(new Country("Malawi", "265"));
                 add(new Country("Mauritius", "230"));
                 add(new Country("Mozambique", "258"));
                 add(new Country("Rwanda", "250"));
                 add(new Country("Seychelles", "248"));
                 add(new Country("Tanzania", "255"));
                 add(new Country("Uganda", "256"));
                 add(new Country("Djibouti", "253"));
                 add(new Country("Eritrea", "291"));
                 add(new Country("Ethiopia", "251"));
                 add(new Country("Somalia", "252"));
                 add(new Country("Algeria", "213"));
                 add(new Country("Egypt", "20"));
                 add(new Country("Libya", "218"));
                 add(new Country("Morocco", "212"));
                 add(new Country("South Sudan", "211"));
                 add(new Country("Sudan", "249"));
                 add(new Country("Tunisia", "216"));
                 add(new Country("Botswana", "267"));
                 add(new Country("Lesotho", "266"));
                 add(new Country("Namibia", "264"));
                 add(new Country("South Africa", "27"));
                 add(new Country("Zambia", "260" ));
                 add(new Country("Zimbabwe", "263"));
                 add(new Country("Benin", "229"));
                 add(new Country("Burkina Faso", "226"));
                 add(new Country("Cape Verde", "238"));
                 add(new Country("Ivory Coast", "225"));
                 add(new Country("Gambia", "220"));
                 add(new Country("Guinea", "224"));
                 add(new Country("Guinea-Bissau", "245"));
                 add(new Country("Liberia", "231"));
                 add(new Country("Mali", "223"));
                 add(new Country("Mauritania", "222"));
                 add(new Country("Niger", "227"));
                 add(new Country("Senegal", "221"));
                 add(new Country("Sierra Leone", "232"));
                 add(new Country("Togo", "228"));
            }
        };

        countries.forEach(country -> {
            Country fetchCountry = countryRepository.findByName(country.getName());
            if (fetchCountry == null) {
                countryRepository.saveAndFlush(country);
            }
        });
    }



    private void seedStates() {
        List<State> states = new ArrayList<State>() {
            {
                add(new State("Abia", 1l));
                add(new State("Adamawa",1l));
                add(new State("Akwa Ibom",1l));
                add(new State("Anambra",1l));
                add(new State("Bauchi",1l));
                add(new State("Bayelsa",1l));
                add(new State("Benue",1l));
                add(new State("Borno",1l));
                add(new State("Cross River",1l));
                add(new State("Delta",1l));
                add(new State("Ebonyi",1l));
                add(new State("Edo",1l));
                add(new State("Ekiti",1l));
                add(new State("Enugu",1l));
                add(new State("FCT Abuja",1l));
                add(new State("Gombe",1l));
                add(new State("Imo",1l));
                add(new State("Jigawa",1l));
                add(new State("Kaduna",1l));
                add(new State("Kano",1l));
                add(new State("Katsina",1l));
                add(new State("Kebbi",1l));
                add(new State("Kogi",1l));
                add(new State("Kwara",1l));
                add(new State("Lagos",1l));
                add(new State("Nasarawa",1l));
                add(new State("Niger",1l));
                add(new State("Ogun",1l));
                add(new State("Ondo",1l));
                add(new State("Osun",1l));
                add(new State("Oyo",1l));
                add(new State("Plateau",1l));
                add(new State("Rivers",1l));
                add(new State("Sokoto",1l));
                add(new State("Taraba",1l));
                add(new State("Yobe",1l));
                add(new State("Zamfara",1l));
                add(new State("Lagos_OFS",1l));
            }
        };

        states.forEach(state -> {
            State fetchState = stateRepository.findByName(state.getName());
            if (fetchState == null) {
                stateRepository.saveAndFlush(state);
            }
        });
    }







    private void seedLocalGovernments() {
        List<LGA> localGovernments = new ArrayList<LGA>() {
            {
                add(new LGA("Aba North", 1l));
                add(new LGA("Aba South", 1l));
                add(new LGA("Arochukwu", 1l));
                add(new LGA("Bende", 1l));
                add(new LGA("Ikwuano", 1l));
                add(new LGA("Isiala Ngwa North", 1l));
                add(new LGA("Isiala Ngwa South", 1l));
                add(new LGA("Isuikwuato", 1l));
                add(new LGA("Obingwa", 1l));
                add(new LGA("Ohafia", 1l));
                add(new LGA("Osisioma", 1l));
                add(new LGA("Ugwunagbo", 1l));
                add(new LGA("Ukwa East", 1l));
                add(new LGA("Ukwa  West", 1l));
                add(new LGA("Umuahia North", 1l));
                add(new LGA("Umuahia  South", 1l));
                add(new LGA("Umu - Nneochi", 1l));
                add(new LGA("Demsa", 2l));
                add(new LGA("Fufore", 2l));
                add(new LGA("Ganye", 2l));
                add(new LGA("Gire 1", 2l));
                add(new LGA("Gombi", 2l));
                add(new LGA("Guyuk", 2l));
                add(new LGA("Hong", 2l));
                add(new LGA("Jada", 2l));
                add(new LGA("Lamurde", 2l));
                add(new LGA("Madagali", 2l));
                add(new LGA("Maiha", 2l));
                add(new LGA("Mayo - Belwa", 2l));
                add(new LGA("Michika", 2l));
                add(new LGA("Mubi North", 2l));
                add(new LGA("Mubi South", 2l));
                add(new LGA("Numan", 2l));
                add(new LGA("Shelleng", 2l));
                add(new LGA("Song", 2l));
                add(new LGA("Toungo", 2l));
                add(new LGA("Yola North", 2l));
                add(new LGA("Yola South", 2l));
                add(new LGA("Abak", 3l));
                add(new LGA("Eastern Obolo", 3l));
                add(new LGA("Eket", 3l));
                add(new LGA("Esit Eket (Uquo)", 3l));
                add(new LGA("Essien Udim", 3l));
                add(new LGA("Etim Ekpo", 3l));
                add(new LGA("Etinan", 3l));
                add(new LGA("Ibeno", 3l));
                add(new LGA("Ibesikpo Asutan", 3l));
                add(new LGA("Ibiono Ibom", 3l));
                add(new LGA("Ika", 3l));
                add(new LGA("Ikono", 3l));
                add(new LGA("Ikot Abasi", 3l));
                add(new LGA("Ikot Ekpene", 3l));
                add(new LGA("Ini", 3l));
                add(new LGA("Itu", 3l));
                add(new LGA("Mbo", 3l));
                add(new LGA("Mkpat Enin", 3l));
                add(new LGA("Nsit Atai", 3l));
                add(new LGA("Nsit Ibom", 3l));
                add(new LGA("Nsit Ubium", 3l));
                add(new LGA("Obot Akara", 3l));
                add(new LGA("Okobo", 3l));
                add(new LGA("Onna", 3l));
                add(new LGA("Oron", 3l));
                add(new LGA("Oruk Anam", 3l));
                add(new LGA("Udung Uko", 3l));
                add(new LGA("Ukanafun", 3l));
                add(new LGA("Uruan", 3l));
                add(new LGA("Urue Offong/Oruko", 3l));
                add(new LGA("Uyo", 3l));
                add(new LGA("Aguata", 4l));
                add(new LGA("Ayamelum", 4l));
                add(new LGA("Anambra East", 4l));
                add(new LGA("Anambra West", 4l));
                add(new LGA("Anaocha", 4l));
                add(new LGA("Awka North", 4l));
                add(new LGA("Awka South", 4l));
                add(new LGA("Dunukofia", 4l));
                add(new LGA("Ekwusigo", 4l));
                add(new LGA("Idemili North", 4l));
                add(new LGA("Idemili-South", 4l));
                add(new LGA("Ihala", 4l));
                add(new LGA("Njikoka", 4l));
                add(new LGA("Nnewi North", 4l));
                add(new LGA("Nnewi South", 4l));
                add(new LGA("Ogbaru", 4l));
                add(new LGA("Onitsha-North", 4l));
                add(new LGA("Onitsha -South", 4l));
                add(new LGA("Orumba North", 4l));
                add(new LGA("Orumba  South", 4l));
                add(new LGA("Oyi", 4l));
                add(new LGA("Alkaleri", 5l));
                add(new LGA("Bauchi", 5l));
                add(new LGA("Bogoro", 5l));
                add(new LGA("Dambam", 5l));
                add(new LGA("Darazo", 5l));
                add(new LGA("Dass", 5l));
                add(new LGA("Gamawa", 5l));
                add(new LGA("Ganjuwa", 5l));
                add(new LGA("Giade", 5l));
                add(new LGA("Itas/Gadau", 5l));
                add(new LGA("Jama'Are", 5l));
                add(new LGA("Katagum", 5l));
                add(new LGA("Kirfi", 5l));
                add(new LGA("Misau", 5l));
                add(new LGA("Ningi", 5l));
                add(new LGA("Shira", 5l));
                add(new LGA("Tafawa Balewa", 5l));
                add(new LGA("Toro", 5l));
                add(new LGA("Warji", 5l));
                add(new LGA("Zaki", 5l));
                add(new LGA("Brass", 6l));
                add(new LGA("Ekeremor", 6l));
                add(new LGA("Kolokuma/Opokuma", 6l));
                add(new LGA("Nembe", 6l));
                add(new LGA("Ogbia", 6l));
                add(new LGA("Sagbama", 6l));
                add(new LGA("Southern Ijaw", 6l));
                add(new LGA("Yenagoa", 6l));
                add(new LGA("Ado", 7l));
                add(new LGA("Agatu", 7l));
                add(new LGA("Apa", 7l));
                add(new LGA("Buruku", 7l));
                add(new LGA("Gboko", 7l));
                add(new LGA("Guma", 7l));
                add(new LGA("Gwer East", 7l));
                add(new LGA("Gwer West", 7l));
                add(new LGA("Katsina-Ala", 7l));
                add(new LGA("Konshisha", 7l));
                add(new LGA("Kwande", 7l));
                add(new LGA("Logo", 7l));
                add(new LGA("Makurdi", 7l));
                add(new LGA("Obi", 7l));
                add(new LGA("Ogbadibo", 7l));
                add(new LGA("Oju", 7l));
                add(new LGA("Ohimini", 7l));
                add(new LGA("Okpokwu", 7l));
                add(new LGA("Otukpo", 7l));
                add(new LGA("Tarka", 7l));
                add(new LGA("Ukum", 7l));
                add(new LGA("Ushongo", 7l));
                add(new LGA("Vandeikya", 7l));
                add(new LGA("Abadam", 8l));
                add(new LGA("Askira / Uba", 8l));
                add(new LGA("Bama", 8l));
                add(new LGA("Bayo", 8l));
                add(new LGA("Biu", 8l));
                add(new LGA("Chibok", 8l));
                add(new LGA("Damboa", 8l));
                add(new LGA("Dikwa", 8l));
                add(new LGA("Gubio", 8l));
                add(new LGA("Guzamala", 8l));
                add(new LGA("Gwoza", 8l));
                add(new LGA("Hawul", 8l));
                add(new LGA("Jere", 8l));
                add(new LGA("Kaga", 8l));
                add(new LGA("Kala Balge", 8l));
                add(new LGA("Konduga", 8l));
                add(new LGA("Kukawa", 8l));
                add(new LGA("Kwaya / Kusar", 8l));
                add(new LGA("Mafa", 8l));
                add(new LGA("Magumeri", 8l));
                add(new LGA("Maiduguri M. C.", 8l));
                add(new LGA("Marte", 8l));
                add(new LGA("Mobbar", 8l));
                add(new LGA("Monguno", 8l));
                add(new LGA("Ngala", 8l));
                add(new LGA("Nganzai", 8l));
                add(new LGA("Shani", 8l));
                add(new LGA("Abi", 9l));
                add(new LGA("Akamkpa", 9l));
                add(new LGA("Akpabuyo", 9l));
                add(new LGA("Bakassi", 9l));
                add(new LGA("Bekwarra", 9l));
                add(new LGA("Biase", 9l));
                add(new LGA("Boki", 9l));
                add(new LGA("Calabar Municipality", 9l));
                add(new LGA("Calabar South", 9l));
                add(new LGA("Etung", 9l));
                add(new LGA("Ikom", 9l));
                add(new LGA("Obanliku", 9l));
                add(new LGA("Obubra", 9l));
                add(new LGA("Obudu", 9l));
                add(new LGA("Odukpani", 9l));
                add(new LGA("Ogoja", 9l));
                add(new LGA("Yakurr", 9l));
                add(new LGA("Yala", 9l));
                add(new LGA("Aniocha North", 10l));
                add(new LGA("Aniocha - South", 10l));
                add(new LGA("Bomadi", 10l));
                add(new LGA("Burutu", 10l));
                add(new LGA("Ethiope  East", 10l));
                add(new LGA("Ethiope  West", 10l));
                add(new LGA("Ika North- East", 10l));
                add(new LGA("Ika - South", 10l));
                add(new LGA("Isoko North", 10l));
                add(new LGA("Isoko South", 10l));
                add(new LGA("Ndokwa East", 10l));
                add(new LGA("Ndokwa West", 10l));
                add(new LGA("Okpe", 10l));
                add(new LGA("Oshimili - North", 10l));
                add(new LGA("Oshimili - South", 10l));
                add(new LGA("Patani", 10l));
                add(new LGA("Sapele", 10l));
                add(new LGA("Udu", 10l));
                add(new LGA("Ughelli North", 10l));
                add(new LGA("Ughelli South", 10l));
                add(new LGA("Ukwuani", 10l));
                add(new LGA("Uvwie", 10l));
                add(new LGA("Warri  North", 10l));
                add(new LGA("Warri South", 10l));
                add(new LGA("Warri South  West", 10l));
                add(new LGA("Abakaliki", 11l));
                add(new LGA("Afikpo North", 11l));
                add(new LGA("Afikpo  South", 11l));
                add(new LGA("Ebonyi", 11l));
                add(new LGA("Ezza North", 11l));
                add(new LGA("Ezza South", 11l));
                add(new LGA("Ikwo", 11l));
                add(new LGA("Ishielu", 11l));
                add(new LGA("Ivo", 11l));
                add(new LGA("Izzi", 11l));
                add(new LGA("Ohaozara", 11l));
                add(new LGA("Ohaukwu", 11l));
                add(new LGA("Onicha", 11l));
                add(new LGA("Akoko Edo", 12l));
                add(new LGA("Egor", 12l));
                add(new LGA("Esan Central", 12l));
                add(new LGA("Esan North East", 12l));
                add(new LGA("Esan South East", 12l));
                add(new LGA("Esan West", 12l));
                add(new LGA("Etsako Central", 12l));
                add(new LGA("Etsako East", 12l));
                add(new LGA("Etsako  West", 12l));
                add(new LGA("Igueben", 12l));
                add(new LGA("Ikpoba/Okha", 12l));
                add(new LGA("Oredo", 12l));
                add(new LGA("Orhionmwon", 12l));
                add(new LGA("Ovia North East", 12l));
                add(new LGA("Ovia South West", 12l));
                add(new LGA("Owan East", 12l));
                add(new LGA("Owan West", 12l));
                add(new LGA("Uhunmwode", 12l));
                add(new LGA("Ado Ekiti", 13l));
                add(new LGA("Efon", 13l));
                add(new LGA("Ekiti East", 13l));
                add(new LGA("Ekiti West", 13l));
                add(new LGA("Ekiti South West", 13l));
                add(new LGA("Emure", 13l));
                add(new LGA("Gbonyin", 13l));
                add(new LGA("Ido / Osi", 13l));
                add(new LGA("Ijero", 13l));
                add(new LGA("Ikere", 13l));
                add(new LGA("Ikole", 13l));
                add(new LGA("Ilejemeje", 13l));
                add(new LGA("Irepodun / Ifelodun", 13l));
                add(new LGA("Ise / Orun", 13l));
                add(new LGA("Moba", 13l));
                add(new LGA("Oye", 13l));
                add(new LGA("Aninri", 14l));
                add(new LGA("Awgu", 14l));
                add(new LGA("Enugu East", 14l));
                add(new LGA("Enugu North", 14l));
                add(new LGA("Enugu South", 14l));
                add(new LGA("Ezeagu", 14l));
                add(new LGA("Igbo Etiti", 14l));
                add(new LGA("Igbo Eze North", 14l));
                add(new LGA("Igbo Eze South", 14l));
                add(new LGA("Isi Uzo", 14l));
                add(new LGA("Nkanu East", 14l));
                add(new LGA("Nkanu West", 14l));
                add(new LGA("Nsukka", 14l));
                add(new LGA("Oji-River", 14l));
                add(new LGA("Udenu", 14l));
                add(new LGA("Udi", 14l));
                add(new LGA("Uzo-Uwani", 14l));
                add(new LGA("Abaji", 15l));
                add(new LGA("Bwari", 15l));
                add(new LGA("Gwagwalada", 15l));
                add(new LGA("Kuje", 15l));
                add(new LGA("Kwali", 15l));
                add(new LGA("Municipal", 15l));
                add(new LGA("Akko", 16l));
                add(new LGA("Balanga", 16l));
                add(new LGA("Billiri", 16l));
                add(new LGA("Dukku", 16l));
                add(new LGA("Funakaye", 16l));
                add(new LGA("Gombe", 16l));
                add(new LGA("Kaltungo", 16l));
                add(new LGA("Kwami", 16l));
                add(new LGA("Nafada", 16l));
                add(new LGA("Shongom", 16l));
                add(new LGA("Yalmaltu/ Deba", 16l));
                add(new LGA("Aboh Mbaise", 17l));
                add(new LGA("Ahiazu Mbaise", 17l));
                add(new LGA("Ehime Mbano", 17l));
                add(new LGA("Ezinihitte Mbaise", 17l));
                add(new LGA("Ideato North", 17l));
                add(new LGA("Ideato South", 17l));
                add(new LGA("Ihitte/Uboma (Isinweke)", 17l));
                add(new LGA("Ikeduru (Iho)", 17l));
                add(new LGA("Isiala Mbano (Umuelemai)", 17l));
                add(new LGA("Isu (Umundugba)", 17l));
                add(new LGA("Mbaitoli (Nwaorieubi)", 17l));
                add(new LGA("Ngor Okpala (Umuneke)", 17l));
                add(new LGA("Njaba (Nnenasa)", 17l));
                add(new LGA("Nkwerre", 17l));
                add(new LGA("Nwangele (Onu-Nwangele Amaigbo)", 17l));
                add(new LGA("Obowo (Otoko)", 17l));
                add(new LGA("Oguta (Oguta)", 17l));
                add(new LGA("Ohaji/Egbema (Mmahu-Egbema)", 17l));
                add(new LGA("Okigwe  (Okigwe)", 17l));
                add(new LGA("Onuimo (Okwe)", 17l));
                add(new LGA("Orlu", 17l));
                add(new LGA("Orsu (Awo Idemili)", 17l));
                add(new LGA("Oru-East", 17l));
                add(new LGA("Oru West (Mgbidi)", 17l));
                add(new LGA("Owerri Municipal", 17l));
                add(new LGA("Owerri North (Orie Uratta)", 17l));
                add(new LGA("Owerri West (Umuguma)", 17l));
                add(new LGA("Auyo", 18l));
                add(new LGA("Babura", 18l));
                add(new LGA("Birnin Kudu", 18l));
                add(new LGA("Birniwa", 18l));
                add(new LGA("Buji", 18l));
                add(new LGA("Dutse", 18l));
                add(new LGA("Garki", 18l));
                add(new LGA("Gagarawa", 18l));
                add(new LGA("Gumel", 18l));
                add(new LGA("Guri", 18l));
                add(new LGA("Gwaram", 18l));
                add(new LGA("Gwiwa", 18l));
                add(new LGA("Hadejia", 18l));
                add(new LGA("Jahun", 18l));
                add(new LGA("Kafin Hausa", 18l));
                add(new LGA("Kaugama", 18l));
                add(new LGA("Kazaure", 18l));
                add(new LGA("Kirika Samma", 18l));
                add(new LGA("Kiyawa", 18l));
                add(new LGA("Maigatari", 18l));
                add(new LGA("Malam Madori", 18l));
                add(new LGA("Miga", 18l));
                add(new LGA("Ringim", 18l));
                add(new LGA("Roni", 18l));
                add(new LGA("Sule-Tankarkar", 18l));
                add(new LGA("Taura", 18l));
                add(new LGA("Yankwashi", 18l));
                add(new LGA("Birnin Gwari", 19l));
                add(new LGA("Chikun", 19l));
                add(new LGA("Giwa", 19l));
                add(new LGA("Igabi", 19l));
                add(new LGA("Ikara", 19l));
                add(new LGA("Jaba", 19l));
                add(new LGA("Jema'A", 19l));
                add(new LGA("Kachia", 19l));
                add(new LGA("Kaduna North", 19l));
                add(new LGA("Kaduna South", 19l));
                add(new LGA("Kagarko", 19l));
                add(new LGA("Kajuru", 19l));
                add(new LGA("Kaura", 19l));
                add(new LGA("Kauru", 19l));
                add(new LGA("Kubau", 19l));
                add(new LGA("Kudan", 19l));
                add(new LGA("Lere", 19l));
                add(new LGA("Makarfi", 19l));
                add(new LGA("Sabon Gari", 19l));
                add(new LGA("Sanga", 19l));
                add(new LGA("Soba", 19l));
                add(new LGA("Zangon Kataf", 19l));
                add(new LGA("Zaria", 19l));
                add(new LGA("Ajingi", 20l));
                add(new LGA("Albasu", 20l));
                add(new LGA("Bagwai", 20l));
                add(new LGA("Bebeji", 20l));
                add(new LGA("Bichi", 20l));
                add(new LGA("Bunkure", 20l));
                add(new LGA("Dala", 20l));
                add(new LGA("Danbata", 20l));
                add(new LGA("Dawaki Kudu", 20l));
                add(new LGA("Dawaki Tofa", 20l));
                add(new LGA("Doguwa", 20l));
                add(new LGA("Fagge", 20l));
                add(new LGA("Gabasawa", 20l));
                add(new LGA("Garko", 20l));
                add(new LGA("Garun Malam", 20l));
                add(new LGA("Gaya", 20l));
                add(new LGA("Gezawa", 20l));
                add(new LGA("Gwale", 20l));
                add(new LGA("Gwarzo", 20l));
                add(new LGA("Kabo", 20l));
                add(new LGA("Kano Municipal", 20l));
                add(new LGA("Karaye", 20l));
                add(new LGA("Kibiya", 20l));
                add(new LGA("Kiru", 20l));
                add(new LGA("Kumbotso", 20l));
                add(new LGA("Kunchi", 20l));
                add(new LGA("Kura", 20l));
                add(new LGA("Madobi", 20l));
                add(new LGA("Makoda", 20l));
                add(new LGA("Minjibir", 20l));
                add(new LGA("Nasarawa", 20l));
                add(new LGA("Rano", 20l));
                add(new LGA("Rimin Gado", 20l));
                add(new LGA("Rogo", 20l));
                add(new LGA("Shanono", 20l));
                add(new LGA("Sumaila", 20l));
                add(new LGA("Takai", 20l));
                add(new LGA("Tarauni", 20l));
                add(new LGA("Tofa", 20l));
                add(new LGA("Tsanyawa", 20l));
                add(new LGA("Tudun Wada", 20l));
                add(new LGA("Ungogo", 20l));
                add(new LGA("Warawa", 20l));
                add(new LGA("Wudil", 20l));
                add(new LGA("Bakori", 21l));
                add(new LGA("Batagarawa", 21l));
                add(new LGA("Batsari", 21l));
                add(new LGA("Baure", 21l));
                add(new LGA("Bindawa", 21l));
                add(new LGA("Charanchi", 21l));
                add(new LGA("Dandume", 21l));
                add(new LGA("Danja", 21l));
                add(new LGA("Dan Musa", 21l));
                add(new LGA("Daura", 21l));
                add(new LGA("Dutsi", 21l));
                add(new LGA("Dutsin-Ma", 21l));
                add(new LGA("Faskari", 21l));
                add(new LGA("Funtua", 21l));
                add(new LGA("Ingawa", 21l));
                add(new LGA("Jibia", 21l));
                add(new LGA("Kafur", 21l));
                add(new LGA("Kaita", 21l));
                add(new LGA("Kankara", 21l));
                add(new LGA("Kankia", 21l));
                add(new LGA("Katsina", 21l));
                add(new LGA("Kurfi", 21l));
                add(new LGA("Kusada", 21l));
                add(new LGA("Mai'Adua", 21l));
                add(new LGA("Malufashi", 21l));
                add(new LGA("Mani", 21l));
                add(new LGA("Mashi", 21l));
                add(new LGA("Matazu", 21l));
                add(new LGA("Musawa", 21l));
                add(new LGA("Rimi", 21l));
                add(new LGA("Sabuwa", 21l));
                add(new LGA("Safana", 21l));
                add(new LGA("Sandamu", 21l));
                add(new LGA("Zango", 21l));
                add(new LGA("Aliero", 22l));
                add(new LGA("Arewa", 22l));
                add(new LGA("Argungu", 22l));
                add(new LGA("Augie", 22l));
                add(new LGA("Bagudo", 22l));
                add(new LGA("Birnin Kebbi", 22l));
                add(new LGA("Bunza", 22l));
                add(new LGA("Dandi", 22l));
                add(new LGA("Fakai", 22l));
                add(new LGA("Gwandu", 22l));
                add(new LGA("Jega", 22l));
                add(new LGA("Kalgo", 22l));
                add(new LGA("Koko/Besse", 22l));
                add(new LGA("Maiyama", 22l));
                add(new LGA("Ngaski", 22l));
                add(new LGA("Sakaba", 22l));
                add(new LGA("Shanga", 22l));
                add(new LGA("Suru", 22l));
                add(new LGA("Wasagu/Danko", 22l));
                add(new LGA("Yauri", 22l));
                add(new LGA("Zuru", 22l));
                add(new LGA("Adavi", 23l));
                add(new LGA("Ajaokuta", 23l));
                add(new LGA("Ankpa", 23l));
                add(new LGA("Bassa", 23l));
                add(new LGA("Dekina", 23l));
                add(new LGA("Ibaji", 23l));
                add(new LGA("Idah", 23l));
                add(new LGA("Igalamela/Odolu", 23l));
                add(new LGA("Ijumu", 23l));
                add(new LGA("Kabba/Bunu", 23l));
                add(new LGA("Kogi . K. K.", 23l));
                add(new LGA("Lokoja", 23l));
                add(new LGA("Mopa Moro", 23l));
                add(new LGA("Ofu", 23l));
                add(new LGA("Ogori Mangogo", 23l));
                add(new LGA("Okehi", 23l));
                add(new LGA("Okene", 23l));
                add(new LGA("Olamaboro", 23l));
                add(new LGA("Omala", 23l));
                add(new LGA("Yagba East", 23l));
                add(new LGA("Yagba West", 23l));
                add(new LGA("Asa", 24l));
                add(new LGA("Baruten", 24l));
                add(new LGA("Edu", 24l));
                add(new LGA("Ekiti", 24l));
                add(new LGA("Ifelodun", 24l));
                add(new LGA("Ilorin East", 24l));
                add(new LGA("Ilorin-South", 24l));
                add(new LGA("Ilorin-West", 24l));
                add(new LGA("Irepodun", 24l));
                add(new LGA("Isin", 24l));
                add(new LGA("Kaiama", 24l));
                add(new LGA("Moro", 24l));
                add(new LGA("Offa", 24l));
                add(new LGA("Oke - Ero", 24l));
                add(new LGA("Oyun", 24l));
                add(new LGA("Patigi", 24l));
                add(new LGA("Agege", 25l));
                add(new LGA("Ajeromi/Ifelodun", 25l));
                add(new LGA("Alimosho", 25l));
                add(new LGA("Amuwo-Odofin", 25l));
                add(new LGA("Apapa", 25l));
                add(new LGA("Badagry", 25l));
                add(new LGA("Epe", 25l));
                add(new LGA("Eti-Osa", 25l));
                add(new LGA("Ibeju/Lekki", 25l));
                add(new LGA("Ifako-Ijaye", 25l));
                add(new LGA("Ikeja", 25l));
                add(new LGA("Ikorodu", 25l));
                add(new LGA("Kosofe", 25l));
                add(new LGA("Lagos Island", 25l));
                add(new LGA("Lagos Mainland", 25l));
                add(new LGA("Mushin", 25l));
                add(new LGA("Ojo", 25l));
                add(new LGA("Oshodi/Isolo", 25l));
                add(new LGA("Somolu", 25l));
                add(new LGA("Surulere", 25l));
                add(new LGA("Akwanga", 26l));
                add(new LGA("Awe", 26l));
                add(new LGA("Doma", 26l));
                add(new LGA("Karu", 26l));
                add(new LGA("Keana", 26l));
                add(new LGA("Keffi", 26l));
                add(new LGA("Kokona", 26l));
                add(new LGA("Lafia", 26l));
                add(new LGA("Nasarawa", 26l));
                add(new LGA("Nasarawa Eggon", 26l));
                add(new LGA("Obi", 26l));
                add(new LGA("Toto", 26l));
                add(new LGA("Wamba", 26l));
                add(new LGA("Agaie", 27l));
                add(new LGA("Agwara", 27l));
                add(new LGA("Bida", 27l));
                add(new LGA("Borgu", 27l));
                add(new LGA("Bosso", 27l));
                add(new LGA("Chanchaga", 27l));
                add(new LGA("Edatti", 27l));
                add(new LGA("Gbako", 27l));
                add(new LGA("Gurara", 27l));
                add(new LGA("Katcha", 27l));
                add(new LGA("Kontagora", 27l));
                add(new LGA("Lapai", 27l));
                add(new LGA("Lavun", 27l));
                add(new LGA("Magama", 27l));
                add(new LGA("Mariga", 27l));
                add(new LGA("Mashegu", 27l));
                add(new LGA("Mokwa", 27l));
                add(new LGA("Munya", 27l));
                add(new LGA("Paikoro", 27l));
                add(new LGA("Rafi", 27l));
                add(new LGA("Rijau", 27l));
                add(new LGA("Shiroro", 27l));
                add(new LGA("Suleja", 27l));
                add(new LGA("Tafa", 27l));
                add(new LGA("Wushishi", 27l));
                add(new LGA("Abeokuta North", 28l));
                add(new LGA("Abeokuta South", 28l));
                add(new LGA("Ado Odo-Ota", 28l));
                add(new LGA("Egbado North", 28l));
                add(new LGA("Egbado South", 28l));
                add(new LGA("Ewekoro", 28l));
                add(new LGA("Ifo", 28l));
                add(new LGA("Ijebu East", 28l));
                add(new LGA("Ijebu North", 28l));
                add(new LGA("Ijebu North East", 28l));
                add(new LGA("Ijebu Ode", 28l));
                add(new LGA("Ikenne", 28l));
                add(new LGA("Imeko/Afon", 28l));
                add(new LGA("Ipokia", 28l));
                add(new LGA("Obafemi/Owode", 28l));
                add(new LGA("Odeda", 28l));
                add(new LGA("Odogbolu", 28l));
                add(new LGA("Ogun Water Side", 28l));
                add(new LGA("Remo North", 28l));
                add(new LGA("Sagamu", 28l));
                add(new LGA("Akoko North East", 29l));
                add(new LGA("Akoko North West", 29l));
                add(new LGA("Akoko South East", 29l));
                add(new LGA("Akoko South West", 29l));
                add(new LGA("Akure North", 29l));
                add(new LGA("Akure South", 29l));
                add(new LGA("Ese-Odo", 29l));
                add(new LGA("Idanre", 29l));
                add(new LGA("Ifedore", 29l));
                add(new LGA("Ilaje", 29l));
                add(new LGA("Ileoluji/Okeigbo", 29l));
                add(new LGA("Irele", 29l));
                add(new LGA("Odigbo", 29l));
                add(new LGA("Okitipupa", 29l));
                add(new LGA("Ondo East", 29l));
                add(new LGA("Ondo West", 29l));
                add(new LGA("Ose", 29l));
                add(new LGA("Owo", 29l));
                add(new LGA("Atakumosa East", 30l));
                add(new LGA("Atakumosa West", 30l));
                add(new LGA("Ayedaade", 30l));
                add(new LGA("Ayedire", 30l));
                add(new LGA("Boluwaduro", 30l));
                add(new LGA("Boripe", 30l));
                add(new LGA("Ede North", 30l));
                add(new LGA("Ede South", 30l));
                add(new LGA("Egbedore", 30l));
                add(new LGA("Ejigbo", 30l));
                add(new LGA("Ife Central", 30l));
                add(new LGA("Ifedayo", 30l));
                add(new LGA("Ife East", 30l));
                add(new LGA("Ifelodun", 30l));
                add(new LGA("Ife North", 30l));
                add(new LGA("Ife South", 30l));
                add(new LGA("Ila", 30l));
                add(new LGA("Ilesa East", 30l));
                add(new LGA("Ilesa West", 30l));
                add(new LGA("Irepodun", 30l));
                add(new LGA("Irewole", 30l));
                add(new LGA("Isokan", 30l));
                add(new LGA("Iwo", 30l));
                add(new LGA("Obokun", 30l));
                add(new LGA("Odo-Otin", 30l));
                add(new LGA("Ola-Oluwa", 30l));
                add(new LGA("Olorunda", 30l));
                add(new LGA("Oriade", 30l));
                add(new LGA("Orolu", 30l));
                add(new LGA("Osogbo", 30l));
                add(new LGA("Afijio", 31l));
                add(new LGA("Akinyele", 31l));
                add(new LGA("Atiba", 31l));
                add(new LGA("Atisbo", 31l));
                add(new LGA("Egbeda", 31l));
                add(new LGA("Ibadan North", 31l));
                add(new LGA("Ibadan North East", 31l));
                add(new LGA("Ibadan North West", 31l));
                add(new LGA("Ibadan South-East", 31l));
                add(new LGA("Ibadan South West", 31l));
                add(new LGA("Ibarapa Central", 31l));
                add(new LGA("Ibarapa East", 31l));
                add(new LGA("Ibarapa North", 31l));
                add(new LGA("Ido", 31l));
                add(new LGA("Irepo", 31l));
                add(new LGA("Iseyin", 31l));
                add(new LGA("Itesiwaju", 31l));
                add(new LGA("Iwajowa", 31l));
                add(new LGA("Kajola", 31l));
                add(new LGA("Lagelu", 31l));
                add(new LGA("Ogbomoso North", 31l));
                add(new LGA("Ogbomoso South", 31l));
                add(new LGA("Ogo-Oluwa", 31l));
                add(new LGA("Olorunsogo", 31l));
                add(new LGA("Oluyole", 31l));
                add(new LGA("Ona-Ara", 31l));
                add(new LGA("Oorelope", 31l));
                add(new LGA("Ori Ire", 31l));
                add(new LGA("Oyo East", 31l));
                add(new LGA("Oyo West", 31l));
                add(new LGA("Saki East", 31l));
                add(new LGA("Saki West", 31l));
                add(new LGA("Surulere", 31l));
                add(new LGA("Jos North", 32l));
                add(new LGA("Barikin Ladi", 32l));
                add(new LGA("Bassa", 32l));
                add(new LGA("Bokkos", 32l));
                add(new LGA("Jos East", 32l));
                add(new LGA("Jos South", 32l));
                add(new LGA("Kanam", 32l));
                add(new LGA("Kanke", 32l));
                add(new LGA("Langtang North", 32l));
                add(new LGA("Langtang South", 32l));
                add(new LGA("Mangu", 32l));
                add(new LGA("Mikang", 32l));
                add(new LGA("Pankshin", 32l));
                add(new LGA("Qua'An Pan", 32l));
                add(new LGA("Riyom", 32l));
                add(new LGA("Shendam", 32l));
                add(new LGA("Wase", 32l));
                add(new LGA("Abua-Odual", 33l));
                add(new LGA("Ahoada East", 33l));
                add(new LGA("Ahoada West", 33l));
                add(new LGA("Akuku Toru", 33l));
                add(new LGA("Andoni", 33l));
                add(new LGA("Asari-Toru", 33l));
                add(new LGA("Bonny", 33l));
                add(new LGA("Degema", 33l));
                add(new LGA("Eleme", 33l));
                add(new LGA("Emohua", 33l));
                add(new LGA("Etche", 33l));
                add(new LGA("Gokana", 33l));
                add(new LGA("Ikwerre", 33l));
                add(new LGA("Khana", 33l));
                add(new LGA("Obio/Akpor", 33l));
                add(new LGA("Ogba/Egbema/Ndoni", 33l));
                add(new LGA("Ogu/Bolo", 33l));
                add(new LGA("Okrika", 33l));
                add(new LGA("Omuma", 33l));
                add(new LGA("Opobo/Nkoro", 33l));
                add(new LGA("Oyigbo", 33l));
                add(new LGA("Port Harcourt", 33l));
                add(new LGA("Tai", 33l));
                add(new LGA("Binji", 34l));
                add(new LGA("Bodinga", 34l));
                add(new LGA("Dange/Shuni", 34l));
                add(new LGA("Gada", 34l));
                add(new LGA("Goronyo", 34l));
                add(new LGA("Gudu", 34l));
                add(new LGA("Gwadabawa", 34l));
                add(new LGA("Illela", 34l));
                add(new LGA("Isa", 34l));
                add(new LGA("Kware", 34l));
                add(new LGA("Kebbe", 34l));
                add(new LGA("Rabah", 34l));
                add(new LGA("S/Birni", 34l));
                add(new LGA("Shagari", 34l));
                add(new LGA("Silame", 34l));
                add(new LGA("Sokoto North", 34l));
                add(new LGA("Sokoto South", 34l));
                add(new LGA("Tambuwal", 34l));
                add(new LGA("Tangaza", 34l));
                add(new LGA("Tureta", 34l));
                add(new LGA("Wamakko", 34l));
                add(new LGA("Wurno", 34l));
                add(new LGA("Yabo", 34l));
                add(new LGA("Ardo - Kola", 35l));
                add(new LGA("Bali", 35l));
                add(new LGA("Donga", 35l));
                add(new LGA("Gashaka", 35l));
                add(new LGA("Gassol", 35l));
                add(new LGA("Ibi", 35l));
                add(new LGA("Jalingo", 35l));
                add(new LGA("Karim-Lamido", 35l));
                add(new LGA("Kurmi", 35l));
                add(new LGA("Lau", 35l));
                add(new LGA("Sardauna", 35l));
                add(new LGA("Takum", 35l));
                add(new LGA("Ussa", 35l));
                add(new LGA("Wukari", 35l));
                add(new LGA("Yorro", 35l));
                add(new LGA("Zing", 35l));
                add(new LGA("Bade", 36l));
                add(new LGA("Bursari", 36l));
                add(new LGA("Damaturu", 36l));
                add(new LGA("Fika", 36l));
                add(new LGA("Fune", 36l));
                add(new LGA("Geidam", 36l));
                add(new LGA("Gujba", 36l));
                add(new LGA("Gulani", 36l));
                add(new LGA("Jakusko", 36l));
                add(new LGA("Karasawa", 36l));
                add(new LGA("Machina", 36l));
                add(new LGA("Nangere", 36l));
                add(new LGA("Nguru", 36l));
                add(new LGA("Potiskum", 36l));
                add(new LGA("Tarmuwa", 36l));
                add(new LGA("Yunusari", 36l));
                add(new LGA("Yusufari", 36l));
                add(new LGA("Anka", 37l));
                add(new LGA("Bakura", 37l));
                add(new LGA("Birnin Magaji", 37l));
                add(new LGA("Bukkuyum", 37l));
                add(new LGA("Bungudu", 37l));
                add(new LGA("Gummi", 37l));
                add(new LGA("Gusau", 37l));
                add(new LGA("Kaura Namoda", 37l));
                add(new LGA("Maradun", 37l));
                add(new LGA("Maru", 37l));
                add(new LGA("Shinkafi", 37l));
                add(new LGA("Talata Mafara", 37l));
                add(new LGA("Tsafe", 37l));
                add(new LGA("Zurmi", 37l));
                add(new LGA("Agege_OFS", 38l));
                add(new LGA("Ajeromi/Ifelodun_OFS", 38l));
                add(new LGA("Alimosho_OFS", 38l));
                add(new LGA("Amuwo-Odofin_OFS", 38l));
                add(new LGA("Apapa_OFS", 38l));
                add(new LGA("Badagry_OFS", 38l));
                add(new LGA("Epe_OFS", 38l));
                add(new LGA("Eti-Osa_OFS", 38l));
                add(new LGA("Ibeju/Lekki_OFS", 38l));
                add(new LGA("Ifako-Ijaye_OFS", 38l));
                add(new LGA("Ikeja_OFS", 38l));
                add(new LGA("Ikorodu_OFS", 38l));
                add(new LGA("Kosofe_OFS", 38l));
                add(new LGA("Lagos Island_OFS", 38l));
                add(new LGA("Lagos Mainland_OFS", 38l));
                add(new LGA("Mushin_OFS", 38l));
                add(new LGA("Ojo_OFS", 38l));
                add(new LGA("Oshodi/Isolo_OFS", 38l));
                add(new LGA("Somolu_OFS", 38l));
                add(new LGA("Surulere_OFS", 38l));
            }
        };
        localGovernments.forEach(localGovernment -> {
            LGA fetchLocalGovernment = localGovernmentRepository.findByName(localGovernment.getName());
            if (fetchLocalGovernment == null) {
                localGovernmentRepository.saveAndFlush(localGovernment);
            }
        });
    }



    private void seedBanks() {
        List<Bank> banks = new ArrayList<Bank>() {
            {
                add(new Bank("First Bank of Nigeria Plc","011"));
                add(new Bank("Citibank Nigeria Limited","023"));
                add(new Bank("Heritage Bank Plc","030"));
                add(new Bank("Union Bank of Nigeria","032"));
                add(new Bank("United Bank for Africa Plc","033"));
                add(new Bank("Wema Bank Plc","035"));
                add(new Bank("Access Bank Plc","044"));
                add(new Bank("Ecobank Nigeria Plc","050"));
                add(new Bank("Zenith International Bank Plc","057"));
                add(new Bank("Guaranty Trust Bank Plc","058"));
                add(new Bank("Standard Chartered Bank Plc","068"));
                add(new Bank("Fidelity Bank Plc","070"));
                add(new Bank("Polaris Bank Plc","076"));
                add(new Bank("Keystone Bank Limited","082"));
                add(new Bank("First City Monument Bank Plc","214"));
                add(new Bank("Unity Bank Plc","215"));
                add(new Bank("Stanbic IBTC Bank Plc","221"));
                add(new Bank("Sterling Bank Plc","232"));
                add(new Bank("Jaiz Bank Plc","301"));
            }
        };

        banks.forEach(bank -> {
            Bank fetchBank = bankRepository.findByName(bank.getName());
            if (fetchBank == null) {
                bankRepository.saveAndFlush(bank);
            }
        });
    }




    private void seedIntegrationUsers() {
        User user = userRepo.findByEmail("integration@sabi.com");
        if (user == null) {
            createIntegrationUser();
        }
    }

    private User createIntegrationUser() {
        User sabiUser = new User();
        sabiUser.setFirstName("integration");
        sabiUser.setLastName("integration");
        sabiUser.setPassword(passwordEncoder.encode("000000"));
        sabiUser.setPhone("02163976228");
        sabiUser.setEmail("integration@sabi.com");
        sabiUser.setUsername("integration@sabi.com");
        sabiUser.setLoginAttempts(0);
        sabiUser.setUserCategory(Constants.ADMIN_USER);
        sabiUser.setStatus("1");
        sabiUser.setPasswordChangedOn(LocalDateTime.now());
        sabiUser.setCreatedBy(0L);
        sabiUser.setCreatedDate(LocalDateTime.now());
        sabiUser.setUpdatedDate(LocalDateTime.now());
        userRepo.saveAndFlush(sabiUser);
        return sabiUser;
    }

    private void seedAdminUser() {
        User user = userRepo.findByEmail("globalAdmin@sabi.com");
        if (user == null) {
            createAdminUser();
        }
    }

    private User createAdminUser() {
        User user = new User();
        user.setFirstName("globalAdmin");
        user.setLastName("globalAdmin");
        user.setPassword(passwordEncoder.encode("111111"));
        user.setPhone("08136529363");
        user.setEmail("globalAdmin@sabi.com");
        user.setUsername("globalAdmin@sabi.com");
        user.setLoginAttempts(0);
        user.setUserCategory(Constants.ADMIN_USER);
        user.setStatus("1");
        user.setPasswordChangedOn(LocalDateTime.now());
        user.setCreatedBy(0L);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        userRepo.saveAndFlush(user);
        return user;
    }



    private void seedAppCode() {
        List<AppCodes> appCodes = new ArrayList<AppCodes>() {
            {
                add(new AppCodes("AG","SABI AGENT APPLICATION"));
                add(new AppCodes("LG","SABI LOGISTICS APPLICATION"));
                add(new AppCodes("SP","SABI SUPPLIER APPLICATION"));
                add(new AppCodes("DC","DATA COLLECTION APPLICATION"));
                add(new AppCodes("GA","GLOBAL ADMIN"));

            }
        };

        appCodes.forEach(appCode -> {
            AppCodes fetchApp = appCodesRepository.findByAppCode(appCode.getAppCode());
            if (fetchApp == null) {
                appCodesRepository.saveAndFlush(appCode);
            }
        });
    }

}
