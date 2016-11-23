/**
 * Created by c15aen on 2016-04-13.
 */
/**
 * Skapar en position bestående av en x- och en y-koordinat. Innehåller funktioner för att hämta grannar till
 * positioner samt att kolla om två positioner har samma koordinater.
 */
public class Position {

        private int x;
        private int y;

        /**
         * Skapar en position med en x- och en y-koordinat.
         * @param x - Önskad x-koordinat.
         * @param y - Önskad y-koordinat.
         */

        public Position(int x, int y) {
                this.x = x;
                this.y = y;
        }

        /**
         * @return - Positionens x-värde.
         */

        public int getX() {

                return x;
        }

        /**
         * @return - Positionens y-värde.
         */

        public int getY() {

                return y;
        }

        /**
         * @return - En position söder om denna position. Dvs en position med 1 högre i y-värde.
         */

        public Position getPosToSouth() {
                return new Position(x, (y+1));

        }

        /**
         * @return - En position norr om denna position. Dvs en position med 1 lägre i y-värde.
         */

        public Position getPosToNorth() {
                return new Position(x, (y-1));

        }

        /**
         * @return - En position öster om denna position. Dvs en position med 1 högre i x-värde.
         */

        public Position getPosToEast() {
                return new Position((x+1), y);

        }

        /**
         * @return - En position väster om denna position. Dvs en position med 1 lägre i x-värde.
         */

        public Position getPosToWest() {
                return new Position((x - 1), y);

        }

        /**
         * Byter ut equals till att kolla om två objekt har samma värde istället för om de är samma objekt.
         * @param o - Objektet som skall undersökas.
         * @return - True om objekten har samma värde.
         */

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Position position = (Position) o;

                return x == position.x && y == position.y;

        }

        /**
         * Ser till att positioner med samma x- och y-värde har samma hashcode.
         * @return - En hashcode(int) baserad på vilket x- och y-värde objektet har.
         */

        @Override
        public int hashCode() {
                int result = x;
                result = 31 * result + y;
                return result;
        }
}
