package units;

import units.impl.duck;
import units.impl.lily;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public interface pond_model {
    Integer pond_width=800;
    Integer pond_highth=800;
    Integer lily_amount = 15;
    Integer duck_amount = 10;
    ExecutorService run_pond() throws IOException;
    List<duck> getducks();
    List<lily> getlilies();
    duck find_maxduck();
    lily find_nearlily(duck duck_near);
    void remove_duck(duck e);
    class lilymange {

    }
    class duckmanage{

    }
    class ducklife{

    }
}
