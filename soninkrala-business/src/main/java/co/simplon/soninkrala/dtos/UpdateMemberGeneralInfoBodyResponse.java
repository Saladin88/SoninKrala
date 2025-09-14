package co.simplon.soninkrala.dtos;

public record UpdateMemberGeneralInfoBodyResponse(
        String firstname,
        String lastname,
        String username,
        String profileImage
) {

    @Override
    public String toString() {
        return "UpdateMemberGeneralInfoBodyResponse{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }


}
